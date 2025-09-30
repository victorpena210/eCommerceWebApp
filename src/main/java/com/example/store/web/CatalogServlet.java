package com.example.store.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import com.example.store.service.ProductService;
import com.example.store.model.Product;

@WebServlet(name = "CatalogServlet", urlPatterns = {"/catalog"})
public class CatalogServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart = (Map<Integer,Integer>) session.getAttribute("cartMap");
            if (cart == null) { cart = new LinkedHashMap<>(); 
            cart.merge(id, 1, Integer::sum);
            session.setAttribute("cartMap", cart);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/catalog");
    }
}
