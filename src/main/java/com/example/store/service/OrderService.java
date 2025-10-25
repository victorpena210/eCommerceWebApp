package com.example.store.service;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import com.example.store.model.Order;
import com.example.store.model.OrderItem;

/**
 * Persists an order and its items atomically.
 */
public class OrderService {
    private final DataSource ds;

    public OrderService() {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/app");
        } catch (Exception e) {
            throw new RuntimeException("Failed to init OrderService datasource", e);
        }
    }

    /**
     * Creates an order for the given user from the session cart map.
     * Cart map: productId -> quantity
     * Returns the created Order with id populated.
     */
    public Order createOrder(int userId, Map<Integer, Integer> cart) {
        if (cart == null || cart.isEmpty()) throw new IllegalArgumentException("Cart is empty");

        String priceSql = "SELECT id, price FROM products WHERE id IN (%s)";
        String orderSql = "INSERT INTO orders (user_id, total) VALUES (?, ?)";
        String itemSql  = "INSERT INTO order_items (order_id, product_id, qty, price_each) VALUES (?, ?, ?, ?)";

        // Build IN clause
        String in = String.join(",", Collections.nCopies(cart.size(), "?"));
        priceSql = String.format(priceSql, in);

        try (Connection c = ds.getConnection()) {
            c.setAutoCommit(false);
            try {
                // 1) Fetch current prices for all items
                Map<Integer, Double> prices = new HashMap<>();
                try (PreparedStatement ps = c.prepareStatement(priceSql)) {
                    int idx = 1;
                    for (Integer pid : cart.keySet()) ps.setInt(idx++, pid);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) prices.put(rs.getInt("id"), rs.getDouble("price"));
                    }
                }
                // Validate all product ids resolved
                for (Integer pid : cart.keySet()) {
                    if (!prices.containsKey(pid)) throw new IllegalArgumentException("Product not found: " + pid);
                }

                // 2) Compute total
                double total = 0.0;
                for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
                    int pid = e.getKey();
                    int qty = Optional.ofNullable(e.getValue()).orElse(0);
                    if (qty <= 0) continue;
                    total += prices.get(pid) * qty;
                }
                if (total <= 0) throw new IllegalArgumentException("Cart total is zero");

                // 3) Insert order
                long orderId;
                try (PreparedStatement ps = c.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setBigDecimal(2, new java.math.BigDecimal(String.format("%.2f", total)));
                    int updated = ps.executeUpdate();
                    if (updated != 1) throw new RuntimeException("Could not insert order row");
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new RuntimeException("No generated key for order");
                        orderId = rs.getLong(1);
                    }
                }

                // 4) Insert items (price snapshot captured at time of order)
                try (PreparedStatement ps = c.prepareStatement(itemSql)) {
                    for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
                        int pid = e.getKey();
                        int qty = Optional.ofNullable(e.getValue()).orElse(0);
                        if (qty <= 0) continue;

                        ps.setLong(1, orderId);
                        ps.setInt(2, pid);
                        ps.setInt(3, qty);
                        ps.setBigDecimal(4, new java.math.BigDecimal(String.format("%.2f", prices.get(pid))));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                c.commit();

                Order out = new Order();
                out.setId(orderId);
                out.setUserId(userId);
                out.setTotal(total);
                return out;

            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("createOrder failed", e);
        }
    }

    /**
     * (Optional) Load a single order with items for display/confirmation page.
     */
    public Order findOrderWithItems(long orderId) {
        String oSql = "SELECT id, user_id, created_at, total FROM orders WHERE id = ?";
        String iSql = "SELECT id, order_id, product_id, qty, price_each FROM order_items WHERE order_id = ?";
        try (Connection c = ds.getConnection()) {
            Order o = null;
            try (PreparedStatement ps = c.prepareStatement(oSql)) {
                ps.setLong(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        o = new Order(
                            rs.getLong("id"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getDouble("total")
                        );
                    }
                }
            }
            if (o == null) return null;
            try (PreparedStatement ps = c.prepareStatement(iSql)) {
                ps.setLong(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        o.getItems().add(new OrderItem(
                            rs.getLong("id"),
                            rs.getLong("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("qty"),
                            rs.getDouble("price_each")
                        ));
                    }
                }
            }
            return o;
        } catch (SQLException e) {
            throw new RuntimeException("findOrderWithItems failed", e);
        }
    }
}
