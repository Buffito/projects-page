package Servlets;

import Classes.Developer;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Classes.Admin.establish_connection;

@WebServlet(name = "/DeveloperServlet", urlPatterns = "/DeveloperServlet")
public class DeveloperServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;
    private HttpSession session;

    public void init() throws ServletException{
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/database");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<html>");
        printWriter.println("<head></head>");
        printWriter.println("<body style='background-color:#5992BF;'>");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (Developer.validate(username, password)) {
            session = request.getSession();

            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setMaxInactiveInterval(30*60);
            Cookie user= new Cookie("username", username);
            user.setMaxAge(30*60);
            response.addCookie(user);
            response.sendRedirect("developer.html");

            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

        }else {
            printWriter.print("<p style=\"color:black; font-family:Verdana\">Sorry, the username or password you entered is incorrect.</p>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("web/developerlog.html");
            requestDispatcher.include(request,response);
        }

        printWriter.close();
        printWriter.println("</body>");
        printWriter.println("</html>");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");

        try {
            if (submit.equalsIgnoreCase("Find Project")){
                ResultSet rst = null;
                PreparedStatement stmt = null;
                String title = request.getParameter("title");
                Connection con = establish_connection();
                String query = "select title,project_type,category,sub_category,payment_type,owner,developer,price from projects where title = ?";
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
                        int price = rst.getInt("price");
                        request.setAttribute("title", title);
                        request.setAttribute("category", category);
                        request.setAttribute("sub_category", sub_category);
                        request.setAttribute("payment_type", payment_type);
                        request.setAttribute("owner", owner);
                        request.setAttribute("developer", developer);
                        request.setAttribute("price", price);

                        session.setAttribute("title",title);
                    }
                }


                stmt.close();
                con.close();
                rst.close();
                request.getRequestDispatcher("showDeveloperProject.jsp").forward(request, response);
            } else if (submit.equalsIgnoreCase("Make Offer")){
                ResultSet rst = null;
                PreparedStatement stmt = null;
                int price = Integer.parseInt(request.getParameter("price"));
                String title = (String)session.getAttribute("title");
                int _price = 0;
                Connection con = establish_connection();
                String query1 = "select price from projects where title = ?";
                stmt = con.prepareStatement(query1);
                stmt.setString(1, title);
                rst = stmt.executeQuery();

                while (rst.next()) {
                    _price = rst.getInt("price");
                }


                if (price < _price){
                    ResultSet rst2 = null;
                    PreparedStatement stmt2 = null;
                    Connection con2 = establish_connection();
                    String query2 = "update projects set pending_developer=?, pending_price=? where title=?";
                    stmt2 = con2.prepareStatement(query2);
                    stmt2.setString(1, (String) session.getAttribute("username"));
                    stmt2.setInt(2, price);
                    stmt2.setString(3, title);
                    stmt2.executeUpdate();
                    
                    stmt2.close();
                    con2.close();
                }

                stmt.close();
                con.close();
                rst.close();

                request.getRequestDispatcher("developer.html").forward(request, response);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
