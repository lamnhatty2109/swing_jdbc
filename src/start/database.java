package start;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class database {
    public static Connection conn = null;
    public static String dbURL = "";
    static String user = "";
    static String pass = "";

    static void connect(String host, String port, String dbName, String username, String password) throws SQLException {
        try {
            dbURL = "jdbc:sqlserver://"+ host + ":"+ port+ ";"
                    + "databaseName=" + dbName + ";"
                    + "integratedSecurity=true";
            user = username;
            pass = password;
            conn = DriverManager.getConnection(dbURL, user, pass);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
             } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static List<hocsinh> getHS() throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        List<hocsinh> ds = new ArrayList<hocsinh>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs;

                rs = stmt.executeQuery("SELECT * FROM hocsinh");
                while ( rs.next() ) {
                    hocsinh hs = new hocsinh();
                    hs.mahs = rs.getString("MHS");
                    hs.tenhs = rs.getString("nameHS");
                    hs.diem = Float.parseFloat(rs.getString("score"));
                    hs.hinh = rs.getString("image");
                    hs.diachi = rs.getString("adress");
                    hs.note = rs.getString("note");

                    ds.add(hs);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            conn.close();
        }
        return ds;
    }

    public static void close() throws SQLException {
        conn.close();
    }
}
