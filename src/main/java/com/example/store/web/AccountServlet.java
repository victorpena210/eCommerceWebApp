package com.example.store.web;

import java.io.IOException;
import java.util.List;

import com.example.store.model.Order;
import com.example.store.model.User;
import com.example.store.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?redirect=" + req.getContextPath() + "/account");
            return;
        }

        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
    }
}