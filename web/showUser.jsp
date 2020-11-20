<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show User</title>
   
    <link rel="stylesheet" href="css/loginpages.css">
    <link rel="stylesheet" href="css/menu_style.css">
</head>
<body>
<ul class="topnav">
  <li><a href="index.jsp">Menu</a></li>
  <li class="right"><a href="log_out_page.jsp">Log Out</a></li>
</ul>
 <div class="myDiv">
     <h2>&nbsp;Show User</h2>
    <br>
    <center>
<form action="GuestServlet" method="post">
            <% if (request.getAttribute("username") != null) {%>
            <br><b>Username: </b><br>
            <%= request.getAttribute("username") %>
            <br><br><b>Name: </b><br>
            <%= request.getAttribute("first_name") %>
            <br><br><b>Surname: </b><br>
            <%= request.getAttribute("last_name") %>
            <br><br><b>Gender: </b><br>
            <%= request.getAttribute("gender") %>
            <% if (request.getAttribute("speciality") != null){ %>
            <br><br><b>Speciality: </b><br>
            <%= request.getAttribute("speciality") %>
            <% } %>
            <% } else { %>
            <br><b> User does not exist</b><br>
            <% } %>
</form>
<br>
</center>
</div>
</body>
</html>
