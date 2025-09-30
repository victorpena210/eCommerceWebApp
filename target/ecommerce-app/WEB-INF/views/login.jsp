<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <c:set var ="pageTitle" value="Log in"/>
    <jsp:include page="_header.jspf"/>
    <h1>Log in</h1>
<c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
  <label>Email <input name="email" type="email" required value="${param.email}"/></label><br/>
  <label>Password <input name="password" type="password" required/></label><br/>
  <button>Log in</button>
</form>
<jsp:include page="_footer.jspf"/>
