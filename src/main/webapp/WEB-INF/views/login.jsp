<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Login</title></head>
<body>
  <h1>Login</h1>

  <p><a href="${pageContext.request.contextPath}/">Home</a></p>

  <c:if test="${not empty error}">
    <p style="color:red">${error}</p>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/login" accept-charset="UTF-8">
    <label>Email: <input type="email" name="email" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <button type="submit">Sign in</button>
  </form>
</body>
</html>
