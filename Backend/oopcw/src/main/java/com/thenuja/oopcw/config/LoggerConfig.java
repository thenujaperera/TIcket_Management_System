package com.thenuja.oopcw.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.*;

/**
 * Configures the logging system for the application, including console, file, and frontend logging.
 */
@Configuration
public class LoggerConfig {
    /**
     * Configures the root logger with custom handlers and formatters.
     */
    public static void configureLogger() {
        try {
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                rootLogger.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            rootLogger.addHandler(fileHandler);

            FrontendLogHandler frontendLogHandler = new FrontendLogHandler();
            frontendLogHandler.setFormatter(new UserFriendlyFormatter());
            rootLogger.addHandler(frontendLogHandler);

            Logger.getLogger("").setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }

}}
