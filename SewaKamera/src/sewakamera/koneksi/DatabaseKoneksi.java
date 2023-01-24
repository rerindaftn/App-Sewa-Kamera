package sewakamera.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//Menghubungkan ke database
public class DatabaseKoneksi {
    Connection conn = null;
    public static Connection koneksi(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String host = "jdbc:mysql://localhost:3306/database_tajirat";
        String user = "root";
        String password = "";
        
        try {
            Class.forName(driver);
            Connection conn = (Connection) DriverManager.getConnection(host,user,password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
