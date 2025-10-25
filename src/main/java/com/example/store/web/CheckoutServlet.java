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

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
	private final OrderService orderService = new OrderService();
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath() + "/login?redirect=" +req.getContextPath() + "/cart");
			return;
		}
		
		User user = (User) session.getAttribute("user");
		
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cartMap");
		if (cart == null || cart.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}
		
		Map<Integer, Integer> clean = new LinkedHashMap<>();
		for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
			Integer q = e.getValue();
			if (q != null && q > 0) clean.put(e.getKey(), q);
		}
		if (clean.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}
		
		try {
			var order = orderService.createOrder(user.getId(), clean);
			session.removeAttribute("cartMap");
			resp.sendRedirect(req.getContextPath() + "/order?orderId=" + order.getId());
		} catch (Exception e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
			
		}
		
	}

}
