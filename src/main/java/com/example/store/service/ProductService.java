package com.example.store.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.store.model.Product;

public class ProductService {
    private static final List<Product> PRODUCTS = new ArrayList<>();
    static {
        PRODUCTS.add(new Product(1, "Hardwood Oak Suffolk Internal Door", "Doors", 109.99));
        PRODUCTS.add(new Product(2, "Oregon Cottage Interior Oak Door", "Doors", 179.99));
        PRODUCTS.add(new Product(3, "Oregon Cottage Horizontal Interior White Oak Door", "Doors", 189.99));
        PRODUCTS.add(new Product(4, "4 Panel Oak Deco Interior Door", "Doors", 209.09));
        PRODUCTS.add(new Product(5, "Worcester 2000 30kW Ng Combi Boiler Includes Free Comfort+ II controller", "Boilers", 989.99));
        PRODUCTS.add(new Product(6, "Glow-worm Betacom 4 30kW Combi Gas Boiler ERP", "Boilers", 787.99));
        PRODUCTS.add(new Product(7, "Worcester 2000 25kW Ng Combi Boiler with Free Comfort+ II controller", "Boilers", 859.99));
    }
    public List<Product> getAll() { return Collections.unmodifiableList(PRODUCTS); }
    public Product findById(int id) { return PRODUCTS.stream().filter(p -> p.getId() == id).findFirst().orElse(null); }
}
