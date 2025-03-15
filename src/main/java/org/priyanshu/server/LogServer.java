package org.priyanshu.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogServer {
    private final int port;
    private final ExecutorService threadPool;
    private volatile boolean isRunning;

    public LogServer(int port) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public void start() throws Exception {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            isRunning = true;
            System.out.println("[Server] Running on port " + port);

            byte[] buffer = new byte[1024];
            while (isRunning) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                threadPool.submit(new LogProcessor(packet));
            }
        }
    }

    public void stop() {
        isRunning = false;
        threadPool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        LogServer server = new LogServer(9876);
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        server.start();
    }

}