package org.ticketmanagement.cli.threads;

import org.ticketmanagement.cli.tickets.Ticket;
import org.ticketmanagement.cli.tickets.StandardTicket;
import org.ticketmanagement.cli.tickets.StandardTicketPool;

import java.util.logging.Logger;

/**
 * Represents a vendor who releases tickets into the ticket pool.
 */
public class TicketVendor extends User {
    private int totalTickets;
    private int releaseRate;
    private static final Logger logger = Logger.getLogger(TicketVendor.class.getName());

    /**
     * Constructor to initialize a ticket vendor.
     * @param vendorID The unique ID of the vendor.
     * @param standardTicketPool The ticket pool to release tickets into.
     * @param totalTickets The total number of tickets to release.
     * @param releaseRate The rate (in milliseconds) at which tickets are released.
     */
    public TicketVendor(String vendorID, StandardTicketPool standardTicketPool, int totalTickets, int releaseRate) {
        super(vendorID, standardTicketPool);
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate * 1000;
    }

    /**
     * Implements the ticket-releasing logic for the vendor.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new StandardTicket(id + "-T" + i, 100.0);

            synchronized (standardTicketPool) {
                standardTicketPool.addTicket(ticket);
                logger.info("Vendor [" + id + "] released ticket: [" + ticket.getTicketID() + "]" +", Remaining tickets in pool: " + standardTicketPool.getRemainingTickets());

            }

            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                logger.info("Vendor thread interrupted");
            }
        }
    }
}

