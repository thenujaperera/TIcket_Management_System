package org.ticketmanagement.cli;

import org.ticketmanagement.cli.config.Configuration;
import org.ticketmanagement.cli.threads.Customer;
import org.ticketmanagement.cli.threads.TicketVendor;
import org.ticketmanagement.cli.tickets.StandardTicketPool;
import org.ticketmanagement.cli.config.LoggerConfig;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main class of the Ticket Management
 * Handles configuration loading, validation, and program execution
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static StandardTicketPool standardTicketPool;
    public static void main(String[] args) {
        LoggerConfig.configureLogger();

        Scanner input = new Scanner(System.in);


        System.out.println("Choose an option:");
        System.out.println("1. Load and view saved configuration");
        System.out.println("2. Enter a new configuration");

        int choice = getValidChoice(input, 1, 2);

        if (choice == 1) {
            Configuration savedConfig = Configuration.loadConfiguration();
            if (savedConfig != null) {
                System.out.println("Saved Configuration");
                System.out.println("Maximum Pool Capacity: "+ savedConfig.getMaxCapacity());
                System.out.println("Number of Vendors: " + savedConfig.getNumVendors());
                System.out.println("Maximum Tickets Released By a Vendor: " + savedConfig.getTotalTickets());
                System.out.println("StandardTicket Release Rate: " + savedConfig.getVendorRate());
                System.out.println("Number of Customers: " + savedConfig.getNumCustomers());
                System.out.println("Maximum Tickets Bought By a Customer: " + savedConfig.getBuyLimit());
                System.out.println("Customer Retrieval Rate: " + savedConfig.getCustomerRate());

                // Ask the user if they want to use the saved configuration
                while (true) {
                    System.out.println("\nWould you like to start the program with this configuration? (yes/no)");
                    String response = input.next();
                    if (response.equalsIgnoreCase("yes")) {
                        startProgram(savedConfig);
                        break;
                    } else if (response.equalsIgnoreCase("no")) {
                        System.out.println("Please enter a new configuration manually:");
                        createNewConfiguration(input); // Call to create a new configuration
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter 'yes' or 'no'.");
                    }
                }
            } else {
                System.out.println("No saved configuration found! Redirecting to create a new configuration.");
                createNewConfiguration(input); // Call to create a new configuration if loading fails
            }
        } else if (choice == 2) {
            createNewConfiguration(input);
        }

    }

    /**
     * Creates a new configuration and starts the program with it.
     * Saves the configuration and starts the ticketing system with it.
     * @param input Scanner instance to get user input
     */
    public static void createNewConfiguration(Scanner input) {
        int maxCapacity = validateInput(input, "Enter Maximum Capacity: ");
        int numVendors = validateInput(input, "Enter Number of Vendors: ");
        int totalTickets = validateInput(input, "Enter Total Number of Tickets released By a Vendor: ");
        int vendorRate = validateInput(input, "Enter vendor release rate (in seconds): ");
        int numCustomers = validateInput(input, "Enter Number of Customers: ");
        int buylimit = validateInput(input, "Enter the maximum number of tickets a customer can buy: ");
        int customerRate = validateInput(input, "Enter customer retrieval rate (in seconds): ");


        Configuration configuration = new Configuration(totalTickets,numVendors,numCustomers, buylimit, vendorRate, customerRate, maxCapacity);
        configuration.saveConfiguration(configuration);
        startProgram(configuration);
    }

    /**
     * Starts the ticketing system with the given configuration.
     * @param configuration Configuration object containing program settings.
     */
    public static void startProgram(Configuration configuration) {
        standardTicketPool = new StandardTicketPool(configuration.getMaxCapacity());
        ArrayList<Thread> threads = new ArrayList<>();

        List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 0; i < configuration.getNumVendors(); i++) {
            Thread vendorThread = new Thread(new TicketVendor("Vendor-" + (i + 1), standardTicketPool, configuration.totalTickets, configuration.vendorRate));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 0; i < configuration.getNumCustomers(); i++) {
            Thread customerThread = new Thread(new Customer("Customer-" + (i + 1), standardTicketPool, configuration.getBuyLimit(), configuration.customerRate));
            customerThreads.add(customerThread);
            customerThread.start();
        }

        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("All tickets processed. Program terminated.");
        System.out.println("All tickets processed. Program terminated.");

    }

    /**
     * Validates integer input with a prompt.
     * @param input input Scanner instance for user input.
     * @param statement Prompt message for the user.
     * @return Validated integer input.
     */
    public static int validateInput(Scanner input, String statement) {
        int num;
        while (true) {
            try {
                System.out.print(statement);
                num = input.nextInt();
                if (num > 0) {
                    return num;
                } else {
                    logger.warning(" value must be greater than zero");
                    System.out.println("Error: value must be greater than zero");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                input.nextLine();
            }
        }
    }

    /**
     * Gets a valid menu choice from the user.
     * Ensures the input is within the specified range and prompts until valid input is provided.
     * @param input Scanner instance for user input.
     * @param min Minimum valid choice.
     * @param max Maximum valid choice.
     * @return Validated menu choice.
     */
    public static int getValidChoice(Scanner input, int min, int max) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    logger.warning("User entered value is out of bounds");
                    System.out.println("Error: Please enter a number between " + min + " and " + max);
                }
            } catch (InputMismatchException e) {
                logger.warning("Invalid Input: Application requires an integer");
                System.out.println("Invalid input! Please enter an integer.");
                input.nextLine();
            }
        }




    }

}