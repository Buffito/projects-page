<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome admin!</title>


    <link rel="stylesheet" href="css/menu_style.css">
    <link rel="stylesheet" href="css/loginpages.css">
    <link rel="stylesheet" href="css/admin_table_style.css">
    <link rel="stylesheet" href="css/admin_tabcontrol_style.css">

    <script>

$(function() {
    $("#test_sub1").click(function(e){

        var number_of_checked_checkbox= $("input[name=test]:checked").length;
        if(number_of_checked_checkbox==0){
            alert("Select someone before submitting");
            return false;
        }else{
            $("#tesy1").submit();
        }

    });

    $("#test_sub2").click(function(e){

        var number_of_checked_checkbox= $("input[name=test]:checked").length;
        if(number_of_checked_checkbox==0){
            alert("Select something before submitting");
            return false;
        }else{
            $("#tesy2").submit();
        }

    });

});
</script>
</head>
<body>
  <ul class="topnav">
  <li><a href="index.jsp">Menu</a></li>
  <li class="right"><a href="log_out_page.jsp">Log Out</a></li>
</ul>
    <%@page import = "java.sql.Connection" %>
    <%@page import = "java.sql.*" %>
    <%@page import = "Classes.Admin" %>

    <% Connection conn1= null,conn2 = null;
    ResultSet rst1 = null,rst2 = null;
    Statement s1 = null,s2 = null;
    String user = null;

   
        if (session.getAttribute("username") == null){
            response.sendRedirect("adminlog.html");
        }
        else user = (String) session.getAttribute("username");


        try {

            conn1 = Admin.establish_connection();
            s1 = conn1.createStatement();
            conn2 = Admin.establish_connection();
            s2 = conn2.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    %>

<div class="tabs">
  <div class="tab-2">
      <label for="tab2-1">Delete User</label>
    <input id="tab2-1" name="tabs-two" type="radio" checked="checked">
    <div>
        <h4>Delete User</h4>
        <div>
            <center><form action="AdminDeletionServlet" method="post" id="tesy1">
          <div id="table1" style="display: block" class="container">
            <table border ="1">
                <tr>
                    <th> Selected&nbsp;&nbsp;&nbsp;&nbsp;</th>
                    <th> Username</th>
                    <th> Name</th>
                    <th> Surname</th>
                    <th> Specialty&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                </tr>

                <% try {
                    rst1 = s1.executeQuery("select username,first_name,last_name,speciality from users");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                    while (rst1.next()) {
                %>

                <tr>
                     <td>
                        <label>
                            <input type="checkbox" name="test" value = "<%= rst1.getString("username") %>"/>
                        </label>&nbsp;</td>
                    <td>   <%= rst1.getString("username") %> </td>
                    <td> <%= rst1.getString("first_name") %> </td>
                    <td> <%= rst1.getString("last_name") %> </td>
                    <td> <%= rst1.getString("speciality") %> </td>
                </tr>
                <% }

                    try {
                        conn1.close();
                        rst1.close();
                        s1.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    %>

            </table>
            <br>
            <input type ="submit" id="test_sub1" class="button2" name="submit" value="Delete User" onclick="if(!this.form.test3.checked){alert('You must select a user before submitting.');return false}"/>
        </div>
    </form></center>
        </div>
    </div>
  </div>
  <div class="tab-2">
      <label for="tab2-2">Delete Project</label>
    <input id="tab2-2" name="tabs-two" type="radio">
    <div>
        <h4>Delete Project</h4>
      <p><form action="AdminDeletionServlet" method="post" id="tesy2">
          <div id="table2" style="display: block" class="container">
            <table border ="1" style ="background-color:white">

                <tr>
                    <th> Selected&nbsp;&nbsp;</th>
                    <th> Title </th>
                </tr>

                <% try {
                    rst2 = s2.executeQuery("select title from projects");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                    while (rst2.next()) {
                %>

                <tr>
                    <td>
                    <label>
                     <input type="checkbox" name="test" value = "<%= rst2.getString("title") %>"/>
                    </label>&nbsp;
                    </td>
                    <td> <%= rst2.getString("title") %> </td>
                 
                        

                </tr>
                <% }

                    try {
                        conn2.close();
                        rst2.close();
                        s2.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                %>

            </table>
            <br>
            <input type ="submit" id="test_sub2" class="button2" name ="submit" value ="Delete Project" onclick="if(!this.form.test3.checked){alert('You must select a project before submitting.');return false}"/>
        </div>
    </form></p>
    </div>
  </div>
</div>
<br>
<br>
</body>
</html>