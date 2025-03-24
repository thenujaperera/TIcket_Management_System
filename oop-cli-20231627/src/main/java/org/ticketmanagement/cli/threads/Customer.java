package org.ticketmanagement.cli.threads;

import org.ticketmanagement.cli.tickets.StandardTicketPool;
import org.ticketmanagement.cli.tickets.Ticket;
import java.util.logging.Logger;

/**
 * Represents a customer who buys tickets from the ticket pool.
 */

public class Customer extends User {
    private int buyCapacity;
    private int retrievalRate;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    /**
     * Constructor to initialize a customer.
     * @param customerID The unique ID of the customer.
     * @param standardTicketPool The ticket pool to buy tickets from.
     * @param buyCapacity The maximum number of tickets the customer can buy.
     * @param retrievalRate The rate (in milliseconds) at which tickets are retrieved.
     */
    public Customer(String customerID, StandardTicketPool standardTicketPool , int buyCapacity, int retrievalRate) {
        super(customerID, standardTicketPool);
        this.buyCapacity = buyCapacity;
        this.retrievalRate = retrievalRate * 1000;
    }

    /**
     * Implements the ticket-buying logic for the customer.
     */
    @Override
    public void run() {
        for (int i = 0; i < buyCapacity; i++) {
            Ticket ticket;

            synchronized (standardTicketPool) {
                ticket = standardTicketPool.buyTicket();
                logger.info("Customer [" + id + "] bought ticket: [" + ticket.getTicketID() + "]"+", Remaining tickets in pool: " + standardTicketPool.getRemainingTickets());
            }

            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                logger.info("Customer thread interrupted");

            }
        }
    }
}

