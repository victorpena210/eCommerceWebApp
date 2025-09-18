package com.example.store.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.store.model.User;

public class UserService {
	
	private final Map<String, User> usersByEmail = new HashMap<>();
	private final AtomicInteger seq = new AtomicInteger(1);
	
	public synchronized User register(String email, String password, String fullName) {
		if (email == null || email.isBlank()) throw new IllegalArgumentException("EMAIL REQUIRED");
		if (password == null || password.isBlank()) throw new IllegalArgumentException("PASSWORD REQUIRED");
		if (usersByEmail.containsKey(email.toLowerCase())) {
			throw new IllegalStateException("EMAIL ALREADY REGISTERD");
		}
		int id = seq.getAndIncrement();
		User u = new User(id, email.trim(), password, fullName == null ? "" : fullName.trim());
		usersByEmail.put(email.toLowerCase(), u);
		return u;
	}
	
	public User findByEmail(String email) {
		if (email == null) return null;
		return usersByEmail.get(email.toLowerCase());
	}
	
	public boolean verify(String email, String password) {
		User u = findByEmail(email);
		return u != null && Objects.equals(u.getPassword(), password);
		
	}
	
	
	
	

}
