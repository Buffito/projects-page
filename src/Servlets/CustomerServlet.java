package Servlets;

import Classes.Customer;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import static Classes.Admin.establish_connection;

@WebServlet(name = "/CustomerServlet", urlPatterns = "/CustomerServlet")
public class CustomerServlet extends HttpServlet {
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


        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(Customer.validate(username,password)){
            session = request.getSession();

            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setMaxInactiveInterval(30*60);

            response.sendRedirect("customer.html");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setHeader ("Expires", "0");
        }else {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<html>");
            printWriter.println("<head></head>");
            printWriter.println("<body style='background-color:#5992BF;'>");
            printWriter.print("<p style=\"color:black; font-family:Verdana\">Sorry, the username or password you entered is incorrect.</p>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("web/customerlog.html");
            requestDispatcher.include(request,response);
            printWriter.close();
            printWriter.println("</body>");
            printWriter.println("</html>");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit.equalsIgnoreCase("Submit")){
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String project_type = request.getParameter("project_type");
            String price_bool = request.getParameter("price_bool");
            String category = request.getParameter("category");
            String sub_category = request.getParameter("sub_category");
            String payment_type = request.getParameter("payment_type");
            String owner = (String) session.getAttribute("username");

            ResultSet rst = null;
            PreparedStatement stmt = null;
            Connection con = establish_connection();
            String query = "insert into projects values (?,?,?,?,?,?,?,?,?)";
            try {
                stmt = con.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, project_type);
                stmt.setString(4, price_bool);
                stmt.setString(5, category);
                stmt.setString(6, sub_category);
                stmt.setString(7, payment_type);
                stmt.setString(8, null);
                stmt.setString(9, owner);


                rst = stmt.executeQuery();

                rst.close();
                stmt.close();
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (submit.equalsIgnoreCase("Give Project")){

            try {
                ResultSet rst3 = null;
                PreparedStatement stmt3 = null;
                String title = request.getParameter("title");
                Connection con3 = establish_connection();
                String query3 = "select price,developer,pending_price,pending_developer from projects where title = ?";
                stmt3 = con3.prepareStatement(query3);
                stmt3.setString(1, title);
                rst3 = stmt3.executeQuery();

                String developer = null;
                int price = 0;
                while (rst3.next()){
                    developer = rst3.getString("pending_developer");
                    price = rst3.getInt("pending_price");
                }
                String p_developer = null;
                PreparedStatement stmt2 = null;
                Connection con2 = establish_connection();
                String query2 = "update projects set developer=?, price=?, pending_price=?,pending_developer=? where title=?";
                stmt2 = con2.prepareStatement(query2);
                int _price = 0;

                stmt2.setString(1, developer);
                stmt2.setInt(2, price);
                stmt2.setInt(3, _price);
                stmt2.setString(4, p_developer);
                stmt2.setString(5, title);
                stmt2.executeUpdate();

                stmt2.close();
                con2.close();

                stmt3.close();
                con3.close();
                rst3.close();

                request.getRequestDispatcher("customer.html").forward(request, response);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
