package com.thenuja.oopcw.config;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handles log messages for the frontend by maintaining a queue of log entries.
 */
public class FrontendLogHandler extends Handler {

    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    /**
     * Publishes a log record to the log queue.
     *
     * @param record the log record to be added to the queue.
     */
    @Override
    public void publish(LogRecord record) {
        if (record != null) {
            String message = getFormatter().format(record);
            logQueue.offer(message); // Add log to queue
        }
    }

    @Override
    public void flush() {
        // implementing on frontend
    }

    @Override
    public void close() throws SecurityException {
        logQueue.clear();
    }

    public static BlockingQueue<String> getLogQueue() {
        return logQueue;
    }
}
