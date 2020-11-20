package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Classes.Admin.establish_connection;

@WebServlet(name = "/GuestServlet", urlPatterns = ("/GuestServlet"))
public class GuestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DataSource datasource = null;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");

        try {
            if (submit.equalsIgnoreCase("Submit")) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String name = request.getParameter("first_name");
                String surname = request.getParameter("last_name");
                String gender = request.getParameter("gender");
                String specialty = request.getParameter("specialty");
                Connection con = establish_connection();
                String query = "insert into users values (?,?,?,?,?,?,?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, name);
                stmt.setString(5, surname);
                stmt.setString(6, gender);
                stmt.setString(7, specialty);

                stmt.executeUpdate();
                if (specialty.equalsIgnoreCase("Customer"))
                    response.sendRedirect("customerlog.html");
                else
                    response.sendRedirect("developerlog.html");
                con.close();
            }
            if (submit.equalsIgnoreCase("Find User")){
                ResultSet rst = null;
                PreparedStatement stmt = null;
                String user = request.getParameter("username");
                Connection con = establish_connection();
                String query = "select username,first_name,last_name,speciality,gender from users where username = ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, user);
                rst = stmt.executeQuery();

                while (rst.next()){
                    String first_name = rst.getString("first_name");
                    String last_name = rst.getString("last_name");
                    String speciality = rst.getString("speciality");
                    String gender = rst.getString("gender");
                    request.setAttribute("username", user);
                    request.setAttribute("first_name", first_name);
                    request.setAttribute("last_name", last_name);
                    request.setAttribute("speciality", speciality);
                    request.setAttribute("gender", gender);
                }
                stmt.close();
                con.close();
                rst.close();
                request.getRequestDispatcher("showUser.jsp").forward(request, response);
            }
            if (submit.equalsIgnoreCase("Find Developer")) {
                ResultSet rst = null;
                PreparedStatement stmt = null;
                String user = request.getParameter("username");
                Connection con = establish_connection();
                String query = "select username,first_name,last_name,gender from users where username = ? and speciality = 'Developer'";
                stmt = con.prepareStatement(query);
                stmt.setString(1, user);
                rst = stmt.executeQuery();

                while (rst.next()) {
                    String first_name = rst.getString("first_name");
                    String last_name = rst.getString("last_name");
                    String gender = rst.getString("gender");
                    request.setAttribute("username", user);
                    request.setAttribute("first_name", first_name);
                    request.setAttribute("last_name", last_name);
                    request.setAttribute("speciality", null);
                    request.setAttribute("gender", gender);
                }
                stmt.close();
                con.close();
                rst.close();
                request.getRequestDispatcher("showUser.jsp").forward(request, response);
            }
            if (submit.equalsIgnoreCase("Find Project")){
                ResultSet rst = null;
                PreparedStatement stmt = null;
                String title = request.getParameter("title");
                Connection con = establish_connection();
                String query = "select title,project_type,category,sub_category,payment_type,owner,developer from projects where title = ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, title);
                rst = stmt.executeQuery();

                while (rst.next()) {
                    String project_type = rst.getString("project_type");
                    if (project_type.equals("Public")){
                        String category = rst.getString("category");
                        String sub_category = rst.getString("sub_category");
                        String payment_type = rst.getString("payment_type");
                        String owner = rst.getString("owner");
                        String developer = rst.getString("developer");
                        request.setAttribute("title", title);
                        request.setAttribute("category", category);
                        request.setAttribute("sub_category", sub_category);
                        request.setAttribute("payment_type", payment_type);
                        request.setAttribute("owner", owner);
                        request.setAttribute("developer", developer);
                    }
                }
                stmt.close();
                con.close();
                rst.close();
                request.getRequestDispatcher("showProject.jsp").forward(request, response);
            }
            if (submit.equalsIgnoreCase("View Description")){
                ResultSet rst = null;
                PreparedStatement stmt = null;
                String title = request.getParameter("title");
                Connection con = establish_connection();
                String query = "select title,description,project_type,category,sub_category,payment_type,owner,developer from projects where title = ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, title);
                rst = stmt.executeQuery();

                while (rst.next()) {
                    String project_type = rst.getString("project_type");
                    if (project_type.equals("Public")){
                        String description = rst.getString("description");
                        String category = rst.getString("category");
                        String sub_category = rst.getString("sub_category");
                        String developer = rst.getString("developer");
                        String owner = rst.getString("owner");
                        request.setAttribute("title", title);
                        request.setAttribute("category", category);
                        request.setAttribute("sub_category", sub_category);
                        request.setAttribute("payment_type", null);
                        request.setAttribute("owner", owner);
                        request.setAttribute("developer", developer);
                    }
                }
                stmt.close();
                con.close();
                rst.close();
                request.getRequestDispatcher("showProject.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
