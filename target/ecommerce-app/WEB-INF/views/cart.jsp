<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.example.store.model.Product" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Your Cart</title></head>
<body>
  <h1>Your Cart</h1>
  <p><a href="<%=request.getContextPath()%>/catalog">Back to Catalog</a></p>
  <%
    List<Product> items = (List<Product>) request.getAttribute("items");
    double total = 0.0;
  %>
  <table border="1">
    <tr><th>Name</th><th>Category</th><th>Price</th></tr>
    <% for (Product p : items) { total += p.getPrice(); %>
      <tr>
        <td><%= p.getName() %></td>
        <td><%= p.getCategory() %></td>
        <td>$<%= String.format("%.2f", p.getPrice()) %></td>
      </tr>
    <% } %>
    <tr><td colspan="2" align="right"><strong>Total</strong></td><td><strong>$<%= String.format("%.2f", total) %></strong></td></tr>
  </table>
</body>
</html>
