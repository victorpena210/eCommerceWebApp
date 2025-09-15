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
            List<Integer> cart = (List<Integer>) session.getAttribute("cart");
            if (cart != null) {
                for (Integer id : cart) {
                    Product p = productService.findById(id);
                    if (p != null) items.add(p);
                }
            }
        }
        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
