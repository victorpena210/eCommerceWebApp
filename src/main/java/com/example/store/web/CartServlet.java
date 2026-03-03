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

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart =
                (session == null) ? null : (Map<Integer, Integer>) session.getAttribute("cartMap");

        List<com.example.store.model.CartLine> lines = new ArrayList<>();
        double total = 0.0;

        if (cart != null && !cart.isEmpty()) {
            for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
                Integer id = e.getKey();
                Integer qtyObj = e.getValue();
                int qty = (qtyObj == null) ? 0 : qtyObj;

                if (qty <= 0) continue;

                Product p = productService.findById(id);
                if (p == null) continue;

                lines.add(new com.example.store.model.CartLine(p, qty));
                total += p.getPrice() * qty;
            }
        }

        req.setAttribute("lines", lines);
        req.setAttribute("total", total);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
