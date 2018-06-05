package com.bindstone.collector.pom;

import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.IDBAccess;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Collector {

    public static void main(String[] params) {
        File file = new File("./collector-pom/pom.xml");
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            try {
                MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
                InputStream is = new FileInputStream(file);
                Model model = mavenXpp3Reader.read(is);
                System.out.println(model.toString());

                String url = "bolt://localhost:7687";
                String user = "neo4j";
                String password = "a";

                Properties props = new Properties();
                props.setProperty(DBProperties.SERVER_ROOT_URI, url);

                IDBAccess db =
                        DBAccessFactory.createDBAccess(DBType.REMOTE, props, user, password);

                db.clearDatabase();

                PomLoader pomLoader = new PomLoader();
                pomLoader.run(model, db);
                
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("No files found !!!");
        }
    }
}
