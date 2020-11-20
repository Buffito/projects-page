package Servlets;

import Classes.Admin;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "/AdminDeletionServlet", urlPatterns = ("/AdminDeletionServlet"))
public class AdminDeletionServlet extends HttpServlet {
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
        String submit = request.getParameter("submit");
        String sql = null;

        if (submit.equalsIgnoreCase("Delete User")){

            String[] results = request.getParameterValues("test");

            for (int i =0; i< results.length; i++)
            {
                String username = results[i];
                sql = "delete from users where username=?";
                Admin.delete(username, sql);
            }
            response.sendRedirect("admin.jsp");
        }

        if (submit.equalsIgnoreCase("Delete project")){
            String[] results = request.getParameterValues("test");

            for (int i =0; i< results.length; i++)
            {
                String title = results[i];
                sql = "delete from projects where title=?";
                Admin.delete(title, sql);
            }
            response.sendRedirect("admin.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
