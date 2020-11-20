package Classes;

import java.sql.*;

import static Classes.Admin.establish_connection;

public class Customer {

    public static boolean validate(String name, String pass) {

        boolean statusc = false;

        Connection conn = establish_connection();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            pst = conn.prepareStatement("select * from users where username=? and password=? and speciality='Customer'");
            pst.setString(1,name);
            pst.setString(2,pass);

            rs = pst.executeQuery();
            statusc = rs.next();


            conn.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return statusc;
    }
}
