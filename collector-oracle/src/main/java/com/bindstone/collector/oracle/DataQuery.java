package com.bindstone.collector.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataQuery {

    private static String ALL_OBJECTS = "select * from ALL_OBJECTS where owner = 'SA'";

    public static StringBuffer getObjects(Connection connection) {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(ALL_OBJECTS);
            return tranform(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static StringBuffer tranform(ResultSet rs) throws SQLException {
        StringBuffer buffer = new StringBuffer();
        int columns = rs.getMetaData().getColumnCount();
        // HEADER
        for (int i = 1; i < columns +1; i++) {
            buffer.append(conv(rs.getMetaData().getColumnName(i)));
            if (i < columns) {
                buffer.append(",");
            }
        }
        buffer.append("\n");
        // DATA
        while(rs.next()) {
            for (int i = 1; i < columns +1; i++) {
                buffer.append(conv(rs.getObject(i)));
                if (i < columns) {
                    buffer.append(",");
                }
            }
            buffer.append("\n");
        }
        return buffer;
    };

    private static String conv(Object object) {
        return "\"" + object + "\"";
    }
}
