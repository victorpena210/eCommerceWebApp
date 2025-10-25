package com.example.store.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Order {
	
	private long id;
	private int userId;
	private Instant createdAt;
	private double total;
	private List<OrderItem> items = new ArrayList<>();
	
	public Order() {}
	
	public Order(long id, int userId, Instant createdAt, double total) {
		this.id = id;
		this.userId = userId;
		this.createdAt = createdAt;
		this.total = total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	

}
