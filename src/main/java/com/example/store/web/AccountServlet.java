package com.example.store.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		Object user = (session == null) ? null : session.getAttribute("user");
		
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login?redirect=" + req.getContextPath() + "/account");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
	}

}
