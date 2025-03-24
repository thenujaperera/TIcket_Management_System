package org.ticketmanagement.cli.config;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Handles the configuration settings for the ticket management system.
 */
public class Configuration {
    public int totalTickets;
    public int numVendors;
    public int numCustomers;
    public int buyLimit;
    public int vendorRate;
    public int customerRate;
    public int maxCapacity;

    public Configuration (int totalTickets,int numVendors,int numCustomers, int buyLimit, int vendorRate, int customerRate, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.numVendors = numVendors;
        this.numCustomers = numCustomers;
        this.buyLimit = buyLimit;
        this.vendorRate = vendorRate;
        this.customerRate = customerRate;
        this.maxCapacity = maxCapacity;
    }

    public int getTotalTickets(){return totalTickets;}
    public int getNumVendors(){return numVendors;}
    public int getNumCustomers(){return numCustomers;}
    public int getMaxCapacity(){return maxCapacity;}
    public int getVendorRate(){return vendorRate;}
    public int getCustomerRate(){return customerRate;}
    public int getBuyLimit(){return buyLimit;}
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    /**
     * Saves the current configuration to a file.
     * @param configuration The configuration object to save.
     */
    public void saveConfiguration (Configuration configuration) {
        try (FileWriter writer = new FileWriter("config_cli.json")) {
            Gson gson = new Gson();
            gson.toJson(configuration, writer);
            logger.info("Configuration saved successfully!");

        }catch (IOException e) {
            logger.warning("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Loads a saved configuration from a file.
     * @return The loaded configuration object, or null if loading fails.
     */
    public static Configuration loadConfiguration(){
        try (FileReader reader = new FileReader("config_cli.json")) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);
            logger.info("Configuration loaded successfully!\n");
            return config;
        } catch (IOException e) {
            logger.warning("Error loading configuration: " + e.getMessage());
            return null;
        }




    }
}

