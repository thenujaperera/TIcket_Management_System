package org.ticketmanagement.cli.tickets;

/**
 * interface provides basic method to be implemented on StandardTicket.
 * Represents a ticket with a unique ID and price.
 */
public interface Ticket {
    String getTicketID();
    double getPrice();
}
