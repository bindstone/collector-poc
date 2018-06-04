package com.bindstone.collector.oracle;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Collector {

    public static void main(String[] parameters) throws SQLException, InterruptedException, IOException {
        Properties config = Config.get();
        Connection connection = DbConnection.get();
        StringBuffer allObjects = DataQuery.getObjects(connection);

        File target = new File(config.getProperty("target"));
        target.mkdirs();
        File allObjectFile = new File(target, "all_object.csv");
        FileUtils.writeStringToFile(allObjectFile, allObjects.toString(), "UTF-8");

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
