package org.priyanshu.server;

import java.net.DatagramPacket;

public class LogProcessor implements Runnable {
    private final DatagramPacket packet;

    public LogProcessor(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        String rawLog = new String(packet.getData(), 0, packet.getLength());

        // Extract log level (e.g., "ERROR: Disk full" → "ERROR")
        String[] parts = rawLog.split(":", 2);
        String level = parts.length > 0 ? parts[0].trim() : "UNKNOWN";

        // Store log
        LogStorage.store(level, rawLog);

        // Track metrics
        PacketLossTracker.trackPacket(rawLog);
    }
}