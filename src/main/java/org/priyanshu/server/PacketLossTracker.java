package org.priyanshu.server;

import java.net.DatagramPacket;
import java.util.concurrent.ConcurrentHashMap;

public class PacketLossTracker {
    // sequence numbers per client (IP+port as key)
    private static final ConcurrentHashMap<String, Integer> clientSequences = new ConcurrentHashMap<>();

    public static void trackPacket(DatagramPacket packet, String log) {
        try {
            String clientId = packet.getAddress().getHostAddress() + ":" + packet.getPort();

            // Extract sequence number
            int currentSeq = Integer.parseInt(log.split(":")[0]);
            int expectedSeq = clientSequences.getOrDefault(clientId, 1);

            if (currentSeq != expectedSeq) {
                System.err.printf("⚠️ Client %s: Missing packets %d-%d%n",
                        clientId, expectedSeq, currentSeq - 1);
            }

            // Update expected sequence
            clientSequences.put(clientId, currentSeq + 1);

        } catch (Exception e) {
            System.err.println("Error tracking packet: " + e.getMessage());
        }
    }
}