package com.example.store.model;

public class OrderItem {
	
	private long id;
	private long orderId;
	private int productId;
	private int qty;
	private double priceEach;
	
	public OrderItem() {}
	
	public OrderItem(long id, long orderId, int productId, int qty, double priceEach) {
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.qty = qty;
		this.priceEach = priceEach;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPriceEach() {
		return priceEach;
	}

	public void setPriceEach(double priceEach) {
		this.priceEach = priceEach;
	}
	
	

}
