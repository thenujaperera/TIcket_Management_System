package org.ticketmanagement.cli.tickets;

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

    public String getTicketID() {
        return ticketID;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TicketID: " + ticketID + ", Price: " + price;
    }
}
