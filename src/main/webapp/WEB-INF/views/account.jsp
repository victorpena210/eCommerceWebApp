<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Account"/>
<jsp:include page="_header.jspf"/>
<h1>Account</h1>
<p>Hello, ${sessionScope.user.email}!</p>
<jsp:include page="_footer.jspf"/>
