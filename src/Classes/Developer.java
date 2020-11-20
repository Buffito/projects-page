package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Developer {
    public static boolean validate(String name, String pass) {

        boolean statusd = false;

        Connection conn = Admin.establish_connection();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            pst = conn.prepareStatement("select * from users where username=? and password=? and speciality='Developer'");
            pst.setString(1,name);
            pst.setString(2,pass);

            rs = pst.executeQuery();
            statusd = rs.next();


            conn.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return statusd;
    }
}
