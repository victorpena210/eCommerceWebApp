package com.example.store.service;

import com.example.store.model.Product;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final DataSource ds;

    public ProductService() {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/app");
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup DataSource jdbc/app", e);
        }
    }

    public List<Product> getAll() {
        String sql = "SELECT id, name, category, price FROM products ORDER BY id";
        List<Product> out = new ArrayList<>();
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query products failed", e);
        }
        return out;
    }

    public Product findById(int id) {
        String sql = "SELECT id, name, category, price FROM products WHERE id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query product by id failed", e);
        }
        return null;
    }
}
