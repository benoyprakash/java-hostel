<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
    <h1><spring:message code="user.list" /></h1>
    <ul>
    <c:forEach items="${users}" var="user">
        <li>
            <c:out value="${user.getId()}" />
        </li>
    </c:forEach>
    </ul>

    <a href="<spring:url value="/user_create.html" />"><spring:message code="user.create" /></a>
</body>
</body>
</html>