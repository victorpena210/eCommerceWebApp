<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Create account"/>
<%@ include file="/WEB-INF/views/_header.jspf" %>

<h1>Create your account</h1>
<c:if test="${not empty error}">
  <div style="color:red">${error}</div>
</c:if>
    
<form method="post" action="${pageContext.request.contextPath}/signup">
  <label>Full Name <input name="fullName" type="fullName" required value="${param.fullName}"/></label><br/>
  <label>Email <input name="email" type="email" required value="${param.email}"/></label><br/>
  <label>Password <input name="password" type="password" required minlength="8"/></label><br/>
  <button type="submit">Create</button>
</form>

<jsp:include page="_footer.jspf"/>
