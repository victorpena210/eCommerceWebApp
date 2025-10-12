package com.example.store.web;

import java.io.IOException;

import com.example.store.model.User;
import com.example.store.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private final UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = trim(req.getParameter("email"));
		String password = req.getParameter("password");
		
		if (email == null || email.isBlank() || password == null || password.isBlank()) {
			req.setAttribute("error", "please enter both email & password");
			req.setAttribute("email", email);
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
			return;
		}
		
		boolean ok = userService.verify(email, password);
		if (!ok) {
			req.setAttribute("error", "invalid email or password");
			req.setAttribute("email", email);
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
			return;
		}
		// this needs tobe fixed. temp fix for now. update userService.verify method
		User user = userService.findByEmail(email);
		if (user != null) {
			user.setPassword(null);
		}
		
		HttpSession session = req.getSession(true);
		req.changeSessionId();
		session.setAttribute("user", user);
		
		String redirect = req.getParameter("redirect");
		if (redirect == null || redirect.isBlank()) {
			redirect = req.getContextPath() + "/account";
		}
		resp.sendRedirect(redirect);

	}
	
	
	
	private static String trim(String s) {
	    return (s == null) ? null : s.trim();
	}


	

}
