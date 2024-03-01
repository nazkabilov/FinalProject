<%@ page import="model.User" %>
<%@ page import="model.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <%@include file="links.jsp"%>
</head>
<body>
<div class="container">
    <%@include file="navbar.jsp"%>
    <br><br>
    <h3 class="text-center">HELLO, dear &nbsp; <%=user.getFullName()%></h3>
    <br><br>
    <form action="/logout" method="get">
        <button class="btn btn-warning">LOGOUT</button>
    </form>
</div>
</body>
</html>
