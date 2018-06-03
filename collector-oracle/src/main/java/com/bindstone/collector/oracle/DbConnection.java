package com.bindstone.collector.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static Connection con;

    private DbConnection() {}

    public static Connection get() {
        if(con == null) {
            Properties properties = Config.get();
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            try {
                con = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("user"),
                        properties.getProperty("password"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return con;
    }
}