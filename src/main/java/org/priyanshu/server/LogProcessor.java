package org.priyanshu.server;

import java.net.DatagramPacket;

public class LogProcessor implements Runnable {
    private final DatagramPacket packet;

    public LogProcessor(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        // Clients send logs in format:
        // <sequence_number>:<log_level>:<message>
        String rawLog = new String(packet.getData(), 0, packet.getLength());

        // Extract log level
        String[] parts = rawLog.split(":", 3);  // expects "seq:level:message"
        String level = parts.length > 1 ? parts[1].trim() : "UNKNOWN";

        // Store log
        LogStorage.store(level, rawLog);

        // Track metrics (now passing the full packet)
        PacketLossTracker.trackPacket(packet, rawLog);
    }
}