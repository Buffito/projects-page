<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Project</title>

    <link rel="stylesheet" href="css/loginpages.css">
    <link rel="stylesheet" href="css/menu_style.css">
</head>
<body>
<ul class="topnav">
    <li><a href="index.jsp">Menu</a></li>
    <li class="right"><a href="log_out_page.jsp">Log Out</a></li>
</ul>
<div class="myDiv">
    <h2>&nbsp;Show Project</h2>
    <br>
    <center>
        <form action="DeveloperServlet" method="get">
            <% if (request.getAttribute("title") != null) {%>
            <br><b>Title: </b><br>
            <%= request.getAttribute("title") %>
            <br><br><b>Category: </b><br>
            <%= request.getAttribute("category") %>
            <br><br><b>Sub Category: </b><br>
            <%= request.getAttribute("sub_category")%>
            <% if (request.getAttribute("payment_type") != null) {%>
            <br><br><b>Payment Type: </b><br>
            <%= request.getAttribute("payment_type") %>
            <br><br><b>Owner: </b><br>
            <%= request.getAttribute("owner") %>
            <br><br><b>Price: </b><br>
            <%= request.getAttribute("price") %>
            <% if (request.getAttribute("description") != null) {%>
            <br><br><b>Description: </b><br>
            <%= request.getAttribute("description") %>
            <% } else {%>
            <br>
            <br><b> Project does not have a description</b><br>
            <% } %>
            <% } %>
            <% } else {%>
            <br><b> Project does not exist or is private</b><br>
            <% } %>
            <br>
            <input onClick="parent.location='projectOffer.html'" value ="Offer" class="button2"/>
        </form><br>
    </center>
</div>
</body>
</html>