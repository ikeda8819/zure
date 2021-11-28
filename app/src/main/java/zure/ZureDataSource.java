package zure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ZureDataSource {

    public ZureDataSource() {
    }

    public static Connection getConnection(String type, String host, String port, String user, String pass,
            String schema, String database) throws SQLException {
        String connectionUrl = SourceType.getUrlPrefixByType(type);
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=public&user=postgres&password=admin";
        // String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        return DriverManager.getConnection(url);
    }

    public static Connection getConnection(String url, String user, String pass) throws SQLException {
        return DriverManager.getConnection(url);
    }

}