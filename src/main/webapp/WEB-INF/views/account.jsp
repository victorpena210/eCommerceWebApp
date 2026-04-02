<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="My Account"/>
<%@ include file="/WEB-INF/views/_header.jspf" %>

<h1>Account</h1>
<p>Hello, ${sessionScope.user.email}!</p>

<h2>My Orders</h2>

<c:choose>
  <c:when test="${empty orders}">
    <p>You have not placed any orders yet.</p>
    <p>
      <a href="${pageContext.request.contextPath}/catalog">Browse products</a>
    </p>
  </c:when>

  <c:otherwise>
    <table border="1" cellpadding="8" cellspacing="0">
      <thead>
        <tr>
          <th>Order #</th>
          <th>Created</th>
          <th>Total</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="order" items="${orders}">
          <tr>
            <td>${order.id}</td>
            <td>${order.createdAt}</td>
            <td>
              $<fmt:formatNumber value="${order.total}" minFractionDigits="2" maxFractionDigits="2"/>
            </td>
            <td>
              <a href="${pageContext.request.contextPath}/order?orderId=${order.id}">View</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/_footer.jspf" %>