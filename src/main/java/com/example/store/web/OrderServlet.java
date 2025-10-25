package com.example.store.web;

import java.io.IOException;

import com.example.store.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {
	private final OrderService orderService = new OrderService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("orderId");
		if (idParam == null) {
			resp.sendRedirect(req.getContextPath() + "/catalog");
			return;
		}
		
		long orderId = Long.parseLong(idParam);
		var order = orderService.findOrderWithItems(orderId);
		if (order == null) {
			resp.sendError(404);
			return;
		}
		req.setAttribute("order", order);
		req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
		
	}

}
