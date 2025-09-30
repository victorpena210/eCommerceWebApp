<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Home"/>

<%@ include file="/WEB-INF/views/_header.jspf" %>

<main style="padding:1rem;">
  <h1>Welcome</h1>
  <p><a href="${pageContext.request.contextPath}/catalog">browse products</a></p>
</main>

<%@ include file="/WEB-INF/views/_footer.jspf" %>
