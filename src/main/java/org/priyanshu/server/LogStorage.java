package org.priyanshu.server;

import java.io.*;
import java.time.Instant;

public class LogStorage {
    private static final String LOG_DIR = "logs/";

    public static void store(String level, String log) {
        String filename = LOG_DIR + level.toLowerCase() + ".log";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(Instant.now() + " | " + log + "\n");
        } catch (IOException e) {
            System.err.println("Failed to store log: " + e.getMessage());
        }
    }
}
