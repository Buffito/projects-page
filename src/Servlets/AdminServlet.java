package Servlets;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/AdminServlet", urlPatterns = ("/AdminServlet"))
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;
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

        if(request.getParameter("username").equals("admin") && request.getParameter("password").equals("pass")){
            HttpSession session = request.getSession();

            session.setAttribute("username", request.getParameter("username"));
            session.setMaxInactiveInterval(30*60);
            Cookie username = new Cookie("username", request.getParameter("username"));
            username.setMaxAge(30*60);
            response.addCookie(username);
            response.sendRedirect("admin.jsp");

            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setHeader ("Expires", "0");


        }else {
            printWriter.print("<p style=\"color:black; font-family:Verdana\">Sorry, the username or password you entered is incorrect.</p>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("web/adminlog.html");
            requestDispatcher.include(request,response);
        }
        printWriter.close();
        printWriter.println("</body>");
        printWriter.println("</html>");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
