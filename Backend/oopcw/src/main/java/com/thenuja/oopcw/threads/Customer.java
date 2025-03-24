package com.thenuja.oopcw.threads;

import com.thenuja.oopcw.tickets.Ticket;
import com.thenuja.oopcw.tickets.StandardTicketPool;

import java.util.logging.Logger;

/**
 * Customer class representing a customer buying tickets from the pool.
 */
public class Customer extends User {
    private int buyCapacity;
    private int retrievalRate;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    public Customer(String customerID, StandardTicketPool standardTicketPool, int buyCapacity, int retrievalRate) {
        super(customerID, standardTicketPool);
        this.buyCapacity = buyCapacity;
        this.retrievalRate = retrievalRate * 1000;
    }

    /**
     * Executes the customer's ticket-buying operations.
     */
    @Override
    public void run() {
        for (int i = 0; i < buyCapacity; i++) {
            if (Thread.currentThread().isInterrupted()) {
                //System.out.println("Customer (" + id + ") interrupted. Stopping thread.");
                logger.info("Customer (" + id + ") interrupted. Stopping thread.");
                break;
            }
            Ticket ticket;
            synchronized (standardTicketPool) {
                ticket = standardTicketPool.buyTicket();
                if (ticket == null) {
                    logger.info("Customer [" + id + "] could not buy a ticket as none are available.");
                    break; // Exit the loop if no tickets are left
                     }
                logger.info("Customer [" + id + "] bought ticket: [" + ticket.getTicketID() + "]"+", Remaining tickets in pool: " + standardTicketPool.getRemainingTickets());
            }
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Customer thread interrupted");
            }
        }
    }
}
