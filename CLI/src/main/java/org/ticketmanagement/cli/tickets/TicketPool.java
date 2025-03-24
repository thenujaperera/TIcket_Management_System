package org.ticketmanagement.cli.tickets;

/**
 *  interface provides basic method to be implemented on StandardTicketPool.
 * Represents a pool of tickets.
 */
public interface TicketPool {
    /**
     * Represents a pool of tickets.
     */
    void addTicket(Ticket ticket);
    /**
     * Represents a pool of tickets.
     */
    Ticket buyTicket();
    /**
     * Gets the number of remaining tickets in the pool.
     */
    int getRemainingTickets();

}
