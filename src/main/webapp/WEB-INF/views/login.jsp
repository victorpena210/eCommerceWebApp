<h2>Sign in</h2>

<c:if test="${not empty error}">
  <div class="alert alert-danger">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
  <input type="hidden" name="redirect" value="${param.redirect}"/>
  <label>Email</label>
  <input type="email" name="email" value="${email}" required />
  <label>Password</label>
  <input type="password" name="password" required />
  <button type="submit">Log in</button>
</form>
