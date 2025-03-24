package com.thenuja.oopcw.controller;

import com.thenuja.oopcw.config.Configuration;
import com.thenuja.oopcw.config.FrontendLogHandler;
import com.thenuja.oopcw.tickets.StandardTicketPool;
import com.thenuja.oopcw.threads.Customer;
import com.thenuja.oopcw.threads.TicketVendor;
import com.thenuja.oopcw.dto.TicketStatisticsDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * REST Controller for managing ticketing system operations.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {

    private boolean systemRunning = false;

    private Configuration configuration; // Remove @Autowired

    private StandardTicketPool standardTicketPool;
    List<Thread> vendorThreads = new ArrayList<>();
    List<Thread> customerThreads = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(TicketController.class.getName());

    public TicketController() {
        logger.info("Initializing TicketController...");
        this.configuration = Configuration.loadConfiguration();
        if (this.configuration != null) {
            logger.info("Loaded configuration from JSON ");
            //logger.log(Level.INFO, "Configuration loaded from JSON: {0}",configuration);
            //System.out.println("Loaded configuration from JSON: " + configuration);
        } else {
            logger.warning("No configuration file found. Default configuration will be used.");
            //System.out.println("No configuration file found. Please set a configuration using the POST /configure endpoint.");
            this.configuration = new Configuration(); // Initialize with default values to prevent null pointer issues
        }
    }

    // Handle POST request to save configuration

    /**
     * Saves a new configuration for the ticketing system.
     * @param config the configuration to save.
     * @return a success message.
     */

    @PostMapping("/configure")
    public String saveConfiguration(@RequestBody Configuration config) {
        //System.out.println("Received configuration: " + config);
        configuration = Configuration.createValidatedConfiguration(
                config.getMaxCapacity(),
                config.getNumVendors(),
                config.getNumCustomers(),
                config.getTotalTickets(),
                config.getVendorRate(),
                config.getCustomerRate(),
                config.getBuyLimit()
        );

        Configuration.saveConfiguration(configuration); // Persist configuration to JSON
        return "Configuration saved successfully!";

    }

    /**
     * Retrieves the current configuration.
     * @return the current Configuration object.
     */
    // Get the current configuration
    @GetMapping("/configuration")
    public Configuration getConfiguration() {

        return configuration;
    }

    /**
     * Starts the ticketing system.
     * @return a success or error message.
     */
    @PostMapping("/start")
    public String startTicketingSystem() {
        if (systemRunning) {
            logger.warning("System already running!");
            return "System already running!";
        }
        if (configuration == null) {
            logger.warning("No configuration is loaded. Please configure the system first");
            return "No configuration is loaded. Please configure the system first using the POST /configure endpoint.";
        }

        standardTicketPool = new StandardTicketPool(configuration.getMaxCapacity());

        // Start vendor threads

        for (int i = 0; i < configuration.getNumVendors(); i++) {
            Thread vendorThread = new Thread(
                    new TicketVendor(
                            "Vendor-" + (i + 1),
                            standardTicketPool,
                            configuration.getTotalTickets(),
                            configuration.getVendorRate()
                    )
            );
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads

        for (int i = 0; i < configuration.getNumCustomers(); i++) {
            Thread customerThread = new Thread(
                    new Customer(
                            "Customer-" + (i + 1),
                            standardTicketPool,
                            configuration.getBuyLimit(),
                            configuration.getCustomerRate()
                    )
            );
            customerThreads.add(customerThread);
            customerThread.start();
        }
        systemRunning=true;
        logger.info("System started!");
        return "Ticketing system started! Check logs for updates.";
    }

    /**
     * Stops the ticketing system.
     * @return a success message.
     */
    @PostMapping("/stop")
    public String stopTicketingSystem() {
        if (!systemRunning) {
            return "System is not running";
        }

        vendorThreads.forEach(thread -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });

        customerThreads.forEach(thread -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });

        vendorThreads.clear();
        customerThreads.clear();
        systemRunning = false;
        logger.info("System stopped!");
        return "System has been stopped successfully";}

    /**
     * Resets the ticketing system.
     *
     * @return a success message.
     */
    @PostMapping("/reset")
    public String resetSystem() {
        standardTicketPool = null;
        logger.info("Reset Successful");
        return "System reset.";
    }

    /**
     * Retrieves log messages.
     * @return a list of log messages.
     */
    @GetMapping("/logs")
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();
        FrontendLogHandler.getLogQueue().drainTo(logs); // Fetch and clear the log queue
        return logs;
    }

    /**
     * Retrieves the ticketing system's current status.
     * @return a TicketStatisticsDTO object containing statistics.
     */
    @GetMapping("/status")
    public TicketStatisticsDTO getTicketStatistics() {
        if (standardTicketPool == null) {
            throw new IllegalStateException("Ticket system is not running. Please start the system first.");
        }
        int ticketsAvailable = standardTicketPool.getRemainingTickets();
        int ticketsSold = standardTicketPool.getTicketsSold();
        int ticketsReleased = standardTicketPool.getTicketsReleased();

        return new TicketStatisticsDTO(ticketsAvailable, ticketsSold, ticketsReleased);
    }
}
