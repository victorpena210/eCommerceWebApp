package com.example.store.model;

public class CartLine {
	private final Product product;
	private final int qty;
	
	public CartLine(Product product, int qty) {
		this.product = product;
		this.qty = qty;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQty() {
		return qty;
	}

    public double getLineTotal() {
        return product.getPrice() * qty;
    }
}