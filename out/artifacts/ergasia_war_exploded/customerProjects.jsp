<html>
<head>
    <title>Show Project</title>

    <link rel="stylesheet" href="css/loginpages.css">
    <link rel="stylesheet" href="css/admin_table_style.css">
    <link rel="stylesheet" href="css/menu_style.css">
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
<%@ page import="Classes.Admin" %>
<%@ page import="java.sql.*" %>
<%@ page import="static Classes.Admin.establish_connection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ResultSet rst = null;
    PreparedStatement stmt = null;
    Connection con = establish_connection();
    String user = null;

    user = (String) session.getAttribute("username");

    try {

        con = Admin.establish_connection();
    }
    catch (Exception e){
        e.printStackTrace();
    }
%>
<body>

<ul class="topnav">
    <li><a href="index.jsp">Menu</a></li>
    <li class="right"><a href="log_out_page.jsp">Log Out</a></li>
</ul>
<div class="myDiv" style="width: 650px">
    <h2>&nbsp;Show Project</h2>
    <br>
    <center>
        <form action="CustomerServlet" method="get">
            <div id="table1" style="display: block" class="container">
                &nbsp;<table style="background-color:white">
                    <tr>
                        <th>Selected&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        <th>Title&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        <th>Developer&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        <th>Price&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        <th>Pending&nbsp;Developer&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        <th>Pending&nbsp;Price&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>

                    </tr>

                    <% try {

                        String query = "select title,developer,price,pending_developer,pending_price from projects where owner=?";
                        stmt = con.prepareStatement(query);
                        stmt.setString(1, user);
                        rst = stmt.executeQuery();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                        while (rst.next()) {
                    %>

                    <tr>
                        <td>
                            <label>
                                <input type="checkbox" name="test" value = "<%= rst.getString("title") %>"/>
                            </label>&nbsp;
                        </td>
                        <td> <%= rst.getString("title") %> </td>
                        <td> <%= rst.getString("developer") %> </td>
                        <td> <%= rst.getInt("price") %> </td>
                        <td> <%= rst.getString("pending_developer") %> </td>
                        <td> <%= rst.getInt("price") %> </td>

                    </tr>
                    <% }

                        try {
                            con.close();
                            rst.close();
                            stmt.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    %>
                </table>
                <br>
                <input type ="submit" id="test_sub2" class="button2" name ="submit" value ="Give Project" onclick="if(!this.form.test3.checked){alert('You must select a project before submitting.');return false}"/>
            </div>
        </form><br>
    </center>
</div>
</body>
</html>