<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Log Out..</title>
</head>

<link rel="stylesheet" href="css/loginpages.css">
<link rel="stylesheet" href="css/menu_style.css">
<body>
 <ul class="topnav">
  <li><a href="index.jsp">Menu</a></li>
</ul>
<%
    String username = null;
//allow access only if session exists
    if(session.getAttribute("username") == null){

        response.sendRedirect("index.jsp");

    }else username = (String) session.getAttribute("username");
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")) username = cookie.getValue();
        }
    }
%>
<div class="myDiv">
    <h2>&nbsp;Logged Out</h2>
    <br>
    <center>
    You have been logged out!<br>
    <br>
    </center>
</div>
<!-- response.encodeURL("logoutservlet") %> -->
<form id="logout" action="LogOutServlet" method="post"> </form>
<script>document.getElementById('logout').submit();</script>

</body>
</html>