package carsharing;

import java.sql.*;

import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

public class DbConn {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    static Connection conn = null;
    static Statement stmt = null;
    static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement(TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    static void execute(String sql){
        try {

            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            System.out.println(e);

        }
    }
    static ResultSet query(String sql) throws SQLException {
        return conn.createStatement(TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
    }
    static void close() throws SQLException {
        stmt.close();
        conn.close();
    }
}
