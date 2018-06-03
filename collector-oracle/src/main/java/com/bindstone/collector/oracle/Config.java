package com.bindstone.collector.oracle;

import java.util.Properties;

public class Config {

    private static Properties properties;

    private Config() {}

    public static Properties get() {
        if (properties == null) {
           properties = new Properties();
           properties.put("user","sa");
           properties.put("password","sa");
           properties.put("url","jdbc:oracle:thin:@localhost:32771:xe");
        }
        return properties;
    }
}
