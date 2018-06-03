package com.bindstone.collector.oracle;

import com.sun.net.httpserver.HttpServer;

import java.util.concurrent.atomic.AtomicBoolean;

public class FileServerRunnable implements Runnable {

    public AtomicBoolean running;

    public FileServerRunnable() {
        super();
        running = new AtomicBoolean(true);
    }

    public void run() {
        System.out.println("Starting Fileserver...");
        HttpServer httpServer = FileServer.get();
        while(running.get()) {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Ending Fileserver...");
        httpServer.stop(0);
    }
}
