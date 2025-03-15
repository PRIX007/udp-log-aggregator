package org.priyanshu.server;

public class PacketLossTracker {
    private static int expectedSeq = 1;

    public static void trackPacket(String log) {
        try {
            // Extract sequence number
            int currentSeq = Integer.parseInt(log.split(":")[0]);

            if (currentSeq != expectedSeq) {
                System.err.printf("⚠️ Missing packets! Expected %d, got %d\n",
                        expectedSeq, currentSeq);
            }
            expectedSeq = currentSeq + 1;
        } catch (Exception ignored) {}
    }
}