package org.ticketmanagement.cli.tickets;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Manages a pool of standard tickets with synchronized access.
 */
public class StandardTicketPool implements TicketPool {
    private final Queue<Ticket> ticketQueue=  new LinkedList<>();
    private int maxCapacity;
    private static final Logger logger = Logger.getLogger(StandardTicketPool.class.getName());

    /**
     * Constructor to initialize the ticket pool with a maximum capacity.
     * @param maxCapacity The maximum number of tickets in the pool.
     */
    public StandardTicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;

    }

    /**
     * Adds a ticket to the pool. Waits if the pool is full.
     * @param ticket The ticket to add to the pool.
     */
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity) {
            try {
                logger.info("TicketPool full. Vendor thread will wait.");
                wait();
            } catch (InterruptedException e) {
                logger.warning("Vendor thread interrupted: " + e.getMessage());
            }
        }
        ticketQueue.add(ticket);
        notifyAll();
    }

    /**
     * Removes and returns a ticket from the pool. Waits if the pool is empty.
     * @return The ticket removed from the pool.
     */
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                logger.info("TicketPool empty. Customer thread will wait for ticket to be released ....");
                wait();
            } catch (InterruptedException e) {
                logger.warning("Customer thread interrupted: " + e.getMessage());
            }
        }
        Ticket ticket = ticketQueue.poll();
        notifyAll();
        return ticket;
    }

    /**
     * Gets the number of remaining tickets in the pool.
     * @return The count of tickets in the pool.
     */
    public synchronized int getRemainingTickets() {
        return ticketQueue.size();
    }
}
