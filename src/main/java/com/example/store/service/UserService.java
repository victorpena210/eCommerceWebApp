package com.example.store.service;

import com.example.store.model.User;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;



// If you want hashing now, see the BCrypt section below.
public class UserService {
    private final DataSource ds;

    public UserService() {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/app");
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup DataSource jdbc/app", e);
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT id, email, password, full_name FROM users WHERE email = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("findByEmail failed", e);
        }
        return null;
    }

    public User register(String email, String password, String fullName) {
    	
        // TODO: hash the password before storing (e.g., BCrypt)
        final String sql = "INSERT INTO users (email, password, full_name) VALUES (?, ?, ?)";

        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, email);
            ps.setString(2, password);   // replace with hash later
            ps.setString(3, fullName);

            int updated = ps.executeUpdate();
            if (updated != 1) throw new RuntimeException("Unexpected row count: " + updated);

            // capture auto-increment id
            long id;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) throw new RuntimeException("No generated key returned");
                id = rs.getLong(1);
            }

            // Build and return the domain object (don’t put raw password on it)
            User user = new User();
            user.setId(id);          // assuming your User has setId(long)
            user.setEmail(email);
            user.setFullName(fullName);
            return user;

        } catch (SQLException e) {
            // MySQL duplicate key is SQLState "23000" and error code 1062
            if ("23000".equals(e.getSQLState()) || e.getErrorCode() == 1062) {
                throw new IllegalArgumentException("Email already in use", e);
            }
            throw new RuntimeException("register failed", e);
        }
    }


    public boolean verify(String email, String password) {
        User u = findByEmail(email);
        // If you turn on BCrypt, compare with BCrypt.checkpw(password, u.getPassword()) instead.
        return u != null && Objects.equals(u.getPassword(), password);
    }
}
