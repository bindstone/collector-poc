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
            File file = new File("/Users/qs/Desktop/export_sql/gen/ALL_OBJECTS.csv");
            httpExchange.sendResponseHeaders(200, file.length());
            OutputStream os = httpExchange.getResponseBody();
            InputStream is = new FileInputStream(file);
            IOUtils.copy(is, os);
            os.close();
            is.close();
        }
    }
}
