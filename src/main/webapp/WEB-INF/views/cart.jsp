<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.example.store.model.Product" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Your Cart</title></head>
<body>
  <h1>Your Cart</h1>
  <p><a href="<%=request.getContextPath()%>/catalog">Back to Catalog</a></p>

  <%
    @SuppressWarnings("unchecked")
    List<Product> items = (List<Product>) request.getAttribute("items");

    // Pull the cart quantities from the session: Map<productId, qty>
    @SuppressWarnings("unchecked")
    Map<Integer,Integer> cartMap = (Map<Integer,Integer>) session.getAttribute("cartMap");

    if (items == null) { items = Collections.emptyList(); }
    if (cartMap == null) { cartMap = Collections.emptyMap(); }

    double total = 0.0;
  %>

  <%
    if (items.isEmpty()) {
  %>
      <p>Your cart is empty.</p>
  <%
    } else {
  %>
      <table border="1" cellpadding="6" cellspacing="0">
        <tr>
          <th>Name</th>
          <th>Category</th>
          <th>Price</th>
          <th>Qty</th>
          <th>Line Total</th>
        </tr>

        <% for (Product p : items) {
             int pid = (int) p.getId(); // adjust if getId() returns long
             Integer qObj = cartMap.get(pid);
             int qty = (qObj == null || qObj <= 0) ? 1 : qObj;   // default to 1 for display
             double line = p.getPrice() * qty;
             total += line;
        %>
          <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getCategory() %></td>
            <td>$<%= String.format("%.2f", p.getPrice()) %></td>
            <td><%= qty %></td>
            <td>$<%= String.format("%.2f", line) %></td>
          </tr>
        <% } %>

        <tr>
          <td colspan="4" align="right"><strong>Total</strong></td>
          <td><strong>$<%= String.format("%.2f", total) %></strong></td>
        </tr>
      </table>

      <!-- Checkout button -->
      <form method="post" action="<%=request.getContextPath()%>/checkout" style="margin-top: 12px;">
        <button type="submit">Checkout</button>
      </form>

      <!-- Optional error message from servlet -->
      <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
      <% } %>
  <%
    } // end else
  %>
</body>
</html>
