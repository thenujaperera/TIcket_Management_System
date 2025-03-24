package com.thenuja.oopcw.config;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formats log messages into a user-friendly format.
 */
public class UserFriendlyFormatter extends Formatter {
    /**
     * Formats a log record into a readable string.
     *
     * @param record the log record to format.
     * @return a formatted log message as a string.
     */
    @Override
    public String format(LogRecord record) {
        String message = record.getMessage();
        String level = record.getLevel().getName();

        switch (level) {
            case "INFO":
                level = "";
                break;
            case "WARNING":
                level = "Warning:  ";
                break;
            case "SEVERE":
                level = "Error:  ";
                break;
            default:
                level = "Log:  ";
        }

        return String.format("%s %s", level, message);
    }
}
