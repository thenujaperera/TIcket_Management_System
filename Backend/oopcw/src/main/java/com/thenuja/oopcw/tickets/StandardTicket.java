package com.thenuja.oopcw.tickets;

/**
 * Represents a standard ticket with a unique ID and price.
 */
public class StandardTicket implements Ticket {
    private final String ticketID;
    private final double price;
    /**
     * Constructor to initialize a standard ticket.
     * @param ticketID The unique ID of the ticket.
     * @param price The price of the ticket.
     */
    public StandardTicket(String ticketID, double price) {
        this.ticketID = ticketID;
        this.price = price;
    }

    @Override
    public String getTicketID() {
        return ticketID;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TicketID: " + ticketID + ", Price: " + price;
    }
}
