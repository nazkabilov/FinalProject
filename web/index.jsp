<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="model.News" %>
<%@ page import="java.util.List" %>
<%@ page import="db.DBManager" %>
<html>
<head>
    <title>Home</title>
    <%@include file="links.jsp"%>
</head>
<body>
<div class="container">
    <%@include file="navbar.jsp"%>
    <%
        List<News> news = DBManager.getAllNews();
        for(News news1 : news){
    %>
    <br>
    <div class="card w-75">
        <div class="card-body">
            <h5 class="card-title"><%=news1.getTitle()%></h5>
            <p class="card-text"><%=news1.getContent()%></p>
            <p><%=user.getFullName()%></p>
            <p><%=news1.getPostDate()%></p>
            <a href="/newsDetails?id=<%=news1.getId()%>" class="btn btn-warning">Details</a>
            <%}%>
        </div>
    </div>

</div>
</body>
</html>
