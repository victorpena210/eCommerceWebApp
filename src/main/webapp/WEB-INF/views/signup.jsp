<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Create your account</h1>
<p><a href="${pageContext.request.contextPath}/">Home</a></p>

<c:if test="${not empty error}">
  <div style="color:red;">${error}</div>
</c:if>


</html>