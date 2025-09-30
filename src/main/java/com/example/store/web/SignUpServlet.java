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


@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {
	
	// In a real app you'd inject a shared singleton; for demo this instance is fine
	private static final UserService userService = new UserService();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fullName = request.getParameter("fullName");
		
		try {
			User user = userService.register(email, password, fullName);
			
			//"log them in" by saving to session
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);
			
			//redirect to catalog (or a welcome page)
			response.sendRedirect(request.getContextPath() + "/catalog");
		} catch (IllegalStateException e) {
			//email already registerd
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
			
		} catch (IllegalArgumentException e) {
			//missing fields
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
			
		}
	}
	
	
	
}