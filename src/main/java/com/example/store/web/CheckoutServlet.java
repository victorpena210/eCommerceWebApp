package com.example.store.web;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.store.model.User;
import com.example.store.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CheckoutServlet", urlPatterns = { "/checkout" })
public class CheckoutServlet extends HttpServlet {
    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Must be logged in to checkout
        if (session == null || session.getAttribute("user") == null) {
            // After login, come back to cart
            resp.sendRedirect(req.getContextPath() + "/login?redirect=" + req.getContextPath() + "/cart");
            return;
        }

        User user = (User) session.getAttribute("user");

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cartMap");

        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Clean out null/zero/negative quantities
        Map<Integer, Integer> clean = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
            Integer qty = e.getValue();
            if (qty != null && qty > 0) {
                clean.put(e.getKey(), qty);
            }
        }

        if (clean.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        try {
            var order = orderService.createOrder(user.getId(), clean);

            // Clear cart on success
            session.removeAttribute("cartMap");

            // Go to order confirmation page
            resp.sendRedirect(req.getContextPath() + "/order?orderId=" + order.getId());
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();

            String msg = root.getMessage();
            if (msg == null || msg.isBlank()) msg = e.toString();

            session.setAttribute("flashError", msg);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}