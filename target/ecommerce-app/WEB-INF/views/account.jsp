<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Account"/>
<%@ include file="/WEB-INF/views/_header.jspf" %>
<h1>Account</h1>
<p>Hello, ${sessionScope.user.email}!</p>
<%@ include file="/WEB-INF/views/_footer.jspf" %>
