<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.store.model.CartLine" %>


<%@ include file="/WEB-INF/views/_header.jspf" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Your Cart</title>
  <style>
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
    th { background: #f4f4f4; }
    .right { text-align: right; }
    .total-row td { font-weight: bold; }
    .actions { margin-top: 16px; display: flex; gap: 12px; }
    .btn { display: inline-block; padding: 10px 14px; border: 1px solid #333; text-decoration: none; }
  </style>
</head>

<body>
<h1>Your Cart</h1>

<%
  @SuppressWarnings("unchecked")
  List<CartLine> lines = (List<CartLine>) request.getAttribute("lines");
  if (lines == null) lines = Collections.emptyList();

  Double totalObj = (Double) request.getAttribute("total");
  double total = (totalObj == null) ? 0.0 : totalObj;
%>

<% if (lines.isEmpty()) { %>
  <p>Your cart is empty.</p>
<% } else { %>

  <table>
    <thead>
    <tr>
      <th>Product</th>
      <th>Category</th>
      <th class="right">Price</th>
      <th class="right">Qty</th>
      <th class="right">Line Total</th>
    </tr>
    </thead>

    <tbody>
    <% for (CartLine line : lines) {
         var p = line.getProduct();
         int qty = line.getQty();
         double price = p.getPrice();
         double lineTotal = line.getLineTotal();
    %>
      <tr>
        <td><%= p.getName() %></td>
        <td><%= (p.getCategory() == null ? "" : p.getCategory()) %></td>
        <td class="right">$<%= String.format("%.2f", price) %></td>
        <td class="right"><%= qty %></td>
        <td class="right">$<%= String.format("%.2f", lineTotal) %></td>
      </tr>
    <% } %>

      <tr class="total-row">
        <td colspan="4" class="right">Total</td>
        <td class="right">$<%= String.format("%.2f", total) %></td>
      </tr>
    </tbody>
  </table>

<div class="actions">

  <form action="<%= request.getContextPath() %>/checkout" method="post" style="display:inline;">
    <button type="submit" class="btn">Checkout</button>
  </form>
</div>

<% } %>

</body>
</html>