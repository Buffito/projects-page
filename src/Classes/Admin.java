package Classes;

import java.sql.*;

public class Admin {

    public static Connection establish_connection () {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost:5432/";
        String dbName = "texnologialogismikou";
        String driver = "org.postgresql.Driver";
        String userName = "shade";
        String password = "1234";

        try {
            conn = DriverManager.getConnection(url + dbName, userName, password);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public static void delete(String username, String delete_sql){
        Connection conn = establish_connection();

        PreparedStatement pst = null;
        try {

            pst = conn.prepareStatement(delete_sql);
            pst.setString(1, username);
            pst.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

}