package com.bindstone.collector.oracle;

import java.sql.Connection;
import java.sql.SQLException;

public class Collector {

    public static void main(String[] parameters) throws SQLException, InterruptedException {
        Connection connection = DbConnection.get();
        System.out.println(DataQuery.getObjects(connection));
        FileServerRunnable runnable = new FileServerRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

        try ( NeoLoader neoLoader = new NeoLoader( "bolt://localhost:7687", "neo4j", "a" ) )
        {
            neoLoader.clear();
            neoLoader.allObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        runnable.running.set(false);
    }
}
