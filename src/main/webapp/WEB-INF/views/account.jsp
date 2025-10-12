<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="My Account"/>
<%@ include file="/WEB-INF/views/_header.jspf" %>
<h1>Account</h1>
<p>Hello, ${sessionScope.user.email}!</p>
<%@ include file="/WEB-INF/views/_footer.jspf" %>
