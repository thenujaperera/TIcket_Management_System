package com.thenuja.oopcw.config;


import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
/**
 * Configuration class manages the settings required for the ticketing system.
 * Provides methods to save and load configurations from a JSON file.
 */
@Component
public class Configuration {

    private int maxCapacity;
    private int numVendors;
    private int numCustomers;
    private int totalTickets;
    private int vendorRate;
    private int customerRate;
    private int buyLimit;
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    /**
     * Constructor with no parameters
     */
    public Configuration() {}

    /**
     * Constructor with all configuration parameters.
     *
     * @param maxCapacity the maximum capacity of the ticket pool.
     * @param numVendors the number of ticket vendors.
     * @param numCustomers the number of customers.
     * @param totalTickets the total number of tickets a vendor can release.
     * @param vendorRate the rate at which vendors release tickets.
     * @param customerRate the rate at which customers buy tickets.
     * @param buyLimit the maximum number of tickets a customer can buy.
     */

    private Configuration(int maxCapacity, int numVendors, int numCustomers, int totalTickets, int vendorRate, int customerRate, int buyLimit) {
        this.maxCapacity = maxCapacity;
        this.numVendors = numVendors;
        this.numCustomers = numCustomers;
        this.totalTickets = totalTickets;
        this.vendorRate = vendorRate;
        this.customerRate = customerRate;
        this.buyLimit = buyLimit;
    }

    /**
     * Creates a validated Configuration object.
     *
     * @param maxCapacity the maximum capacity of the ticket pool.
     * @param numVendors the number of ticket vendors.
     * @param numCustomers the number of customers.
     * @param totalTickets the total number of tickets.
     * @param vendorRate the rate at which vendors release tickets.
     * @param customerRate the rate at which customers buy tickets.
     * @param buyLimit the maximum number of tickets a customer can buy.
     * @return a new Configuration object.
     */
    public static Configuration createValidatedConfiguration(int maxCapacity, int numVendors, int numCustomers, int totalTickets, int vendorRate, int customerRate, int buyLimit) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Maximum capacity must be greater than 0.");
        }
        if (numVendors <= 0) {
            throw new IllegalArgumentException("Number of vendors must be greater than 0.");
        }
        if (numCustomers <= 0) {
            throw new IllegalArgumentException("Number of customers must be greater than 0.");
        }
        if (totalTickets <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        if (vendorRate <= 0) {
            throw new IllegalArgumentException("Vendor rate must be greater than 0.");
        }
        if (customerRate <= 0) {
            throw new IllegalArgumentException("Customer rate must be greater than 0.");
        }
        if (buyLimit <= 0) {
            throw new IllegalArgumentException("Buy limit must be greater than 0.");
        }

        return new Configuration(maxCapacity, numVendors, numCustomers, totalTickets, vendorRate, customerRate, buyLimit);
    }

    // Getters
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getNumVendors() {
        return numVendors;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getVendorRate() {
        return vendorRate;
    }

    public int getCustomerRate() {
        return customerRate;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    /**
     * Saves the current configuration to a file.
     * @param config The configuration object to save.
     */
    public static void saveConfiguration(Configuration config) {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(config, writer);
            logger.info("Configuration saved successfully!");
        } catch (IOException e) {
            logger.warning("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Loads a saved configuration from a file.
     * @return The loaded configuration object, or null if loading fails.
     */
    public static Configuration loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);
            logger.info("Configuration loaded successfully!");
            return config;
        } catch (IOException e) {
            logger.warning("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "maxCapacity=" + maxCapacity +
                ", numVendors=" + numVendors +
                ", numCustomers=" + numCustomers +
                ", totalTickets=" + totalTickets +
                ", vendorRate=" + vendorRate +
                ", customerRate=" + customerRate +
                ", buyLimit=" + buyLimit +
                '}';
    }
}
