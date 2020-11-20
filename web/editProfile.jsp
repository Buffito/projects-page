<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome!</title>
</head>
<ul class="topnav">
  <li class="right"><a href="log_out_page.jsp">Log Out</a></li>
</ul>
        <%@page import = "java.sql.Connection" %>
        <%@page import = "java.sql.*" %>
        <%@ page import="static Classes.Admin.establish_connection" %>

            <% String user = null;


    if (session.getAttribute("username") == null){
        response.sendRedirect("index.jsp");
    }
    else user = (String) session.getAttribute("username");


%>

<link rel="stylesheet" href="css/loginpages.css">
<link rel="stylesheet" href="css/menu_style.css">
<body>
<div class="myDiv">
    <h2>&nbsp;User Information</h2>
    <center>
        <br>
        <label class="label">User ID:</label>
        <input type="text" name="username" required="required" class="custom-input"/><br>
        <label class="label">Password:</label>
        <input type="password" name="password" required="required" class="custom-input"/><br>
        <label class="label">Email:</label>
        <input type="text" name="email" required="required" class="custom-input"/><br>
        <label class="label">Name:</label>
        <input type="text" name="first_name" required="required" class="custom-input"/><br>
        <label class="label">Surname:</label>
        <input type="text" name="last_name" required="required" class="custom-input"/><br><br>
        <label class="label">Gender:</label>
        <select required="required" name="gender" class="select-css">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select> &nbsp;
        <label class="label2">Account type:</label>
        <select required="required" name="speciality" class="select-css">
            <option value="Customer">Customer</option>
            <option value="Developer">Developer</option>
        </select>
        <br><br>
        <input type="reset" value="Clear" class="button2" />
        <input type="submit" value="Submit" class="button2" />
    </center>
    <form action="editProfile.jsp" method="post"><br>
        <% try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String gender = request.getParameter("gender");
            String speciality = request.getParameter("speciality");
            
            PreparedStatement stmt = null;
            Connection con = establish_connection();
            String query = "update users set username = ?, password =?, email =?, first_name =?, last_name =?, gender =?, speciality =? where username = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, first_name);
            stmt.setString(5, last_name);
            stmt.setString(6, gender);
            stmt.setString(7, speciality);
            stmt.setString(8, user);

            stmt.executeUpdate();


            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        %>
    </form>
</div>


</body>
</html>
