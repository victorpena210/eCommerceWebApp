<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.store.model.Order, com.example.store.model.OrderItem, java.util.*" %>
<%
  Order order = (Order) request.getAttribute("order");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order #<%=order.getId()%></title>
</head>
<body>
	<h1>Thanks! Order #<%=order.getId()%></h1>
	<p>Total: $<%= String.format("%.2f", order.getTotal()) %></p>
<table border="1">
    <tr><th>Product ID</th><th>Qty</th><th>Price Each</th><th>Line Total</th></tr>
    <% for (OrderItem it : order.getItems()) { %>
      <tr>
        <td><%= it.getProductId() %></td>
        <td><%= it.getQty() %></td>
        <td>$<%= String.format("%.2f", it.getPriceEach()) %></td>
        <td>$<%= String.format("%.2f", it.getPriceEach() * it.getQty()) %></td>
      </tr>
    <% } %>    
  </table>
        <p><a href="<%=request.getContextPath()%>/catalog">Continue shopping</a></p>
  
</body>
</html>