package org.ticketmanagement.cli.config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Configures the logger for the application.
 */
public class LoggerConfig {

    public static void configureLogger(){
        try {
            FileHandler fileHandler = new FileHandler("application_cli.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            Logger.getLogger("").setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }
}
