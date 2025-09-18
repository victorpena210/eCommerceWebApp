<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.example.store.model.Product" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Catalog</title></head>
<body>
  <h1>Product Catalog</h1>
  <p><a href="<%=request.getContextPath()%>/">Home</a> | <a href="<%=request.getContextPath()%>/cart">Cart</a></p>
  <table border="1">
    <tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th><th></th></tr>
    <%
      List<Product> products = (List<Product>) request.getAttribute("products");
      for (Product p : products) {
    %>
      <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getName() %></td>
        <td><%= p.getCategory() %></td>
        <td>$<%= String.format("%.2f", p.getPrice()) %></td>
        <td>
          <form method="post" action="<%=request.getContextPath()%>/catalog">
            <input type="hidden" name="id" value="<%= p.getId() %>"/>
            <button type="submit">Add to Cart</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
</body>
</html>
