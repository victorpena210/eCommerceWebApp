package com.example.store.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import com.example.store.service.ProductService;
import com.example.store.model.Product;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<Product> items = new ArrayList<>();

        if (session != null) {
            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cartMap");
            if (cart != null && !cart.isEmpty()) {
                for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
                    Integer id = e.getKey();
                    Integer qty = e.getValue();
                    if (qty != null && qty > 0) {
                        Product p = productService.findById(id);
                        if (p != null) {
                            // if you want to show duplicates per quantity:
                            for (int i = 0; i < qty; i++) items.add(p);
                            // OR, if you prefer one row with qty, pass `cart` to the JSP too.
                        }
                    }
                }
            }
        }

        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
