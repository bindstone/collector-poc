package com.bindstone.collector.oracle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

public class FileServer {

    private static HttpServer httpServer;

    private FileServer() {}

    public static HttpServer get() {
        if (httpServer == null) {
            try {
                httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
                httpServer.createContext("/csv/all_objects.csv", new FileHandler());
                httpServer.setExecutor(null);
                httpServer.start();
                return httpServer;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error");
            }
        }
        return httpServer;
    }

    static class FileHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {

            Properties config = Config.get();
            File target = new File(config.getProperty("target"));
            File allObjectFile = new File(target, "all_object.csv");

            System.out.println("File prepared:" + allObjectFile.getAbsolutePath());

            httpExchange.sendResponseHeaders(200, allObjectFile.length());
            OutputStream os = httpExchange.getResponseBody();
            InputStream is = new FileInputStream(allObjectFile);
            IOUtils.copy(is, os);
            os.close();
            is.close();
        }
    }
}
