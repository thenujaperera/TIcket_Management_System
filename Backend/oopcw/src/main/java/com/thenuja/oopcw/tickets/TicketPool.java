package com.thenuja.oopcw.tickets;
/**
 *  interface provides basic method to be implemented on StandardTicketPool.
 * Represents a pool of tickets.
 */
public interface TicketPool {
    void addTicket(Ticket ticket);
    Ticket buyTicket();
    int getRemainingTickets();
    int getTicketsSold();
    int getTicketsReleased();
}
