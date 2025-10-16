package com.example.store.bootstrap;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import java.sql.*;

@WebListener
public class DataSeeder implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/app");
            try (Connection c = ds.getConnection()) {
                // 1) Is the table empty?
                boolean empty;
                try (Statement s = c.createStatement();
                     ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM products")) {
                    rs.next();
                    empty = rs.getLong(1) == 0;
                }
                if (!empty) return;

                // 2) Insert sample products (batch)
                String sql = "INSERT INTO products (name, category, price) VALUES (?, ?, ?)";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    add(ps, "Hardwood Oak Suffolk Internal Door", "Doors", 109.99);
                    add(ps, "Oregon Cottage Interior Oak Door", "Doors", 179.99);
                    add(ps, "Oregon Cottage Horizontal White Oak Door", "Doors", 189.99);
                    add(ps, "4 Panel Oak Deco Interior Door", "Doors", 209.09);
                    add(ps, "Worcester 2000 30kW Combi Boiler (Comfort+ II)", "Boilers", 989.99);
                    add(ps, "Glow-worm Betacom 4 30kW Combi Gas Boiler ERP", "Boilers", 787.99);
                    add(ps, "Worcester 2000 25kW Combi Boiler (Comfort+ II)", "Boilers", 859.99);
                    ps.executeBatch();
                }
                System.out.println("[DataSeeder] Seeded products.");
            }
        } catch (Exception e) {
            System.err.println("[DataSeeder] Seeding failed: " + e.getMessage());
        }
    }

    private static void add(PreparedStatement ps, String name, String cat, double price) throws SQLException {
        ps.setString(1, name);
        ps.setString(2, cat);
        ps.setBigDecimal(3, new java.math.BigDecimal(String.valueOf(price)));
        ps.addBatch();
    }
}
