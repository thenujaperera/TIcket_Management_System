package com.thenuja.oopcw.threads;

import com.thenuja.oopcw.tickets.Ticket;
import com.thenuja.oopcw.tickets.StandardTicket;
import com.thenuja.oopcw.tickets.StandardTicketPool;

import java.util.logging.Logger;
/**
 * TicketVendor class representing a vendor releasing tickets to the pool.
 */
public class TicketVendor extends User {
    private int totalTickets;
    private int releaseRate;
    private static final Logger logger = Logger.getLogger(TicketVendor.class.getName());

    public TicketVendor(String vendorID, StandardTicketPool standardTicketPool, int totalTickets, int releaseRate) {
        super(vendorID, standardTicketPool);
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate * 1000;
    }

    /**
     * Executes the vendor's ticket-releasing operations.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            if (Thread.currentThread().isInterrupted()) {
                //System.out.println("Vendor (" + id + ") interrupted. Exiting thread.");
                logger.info("Vendor (" + id + ") interrupted. Exiting thread.");
                break;
            }
            Ticket ticket = new StandardTicket(id + "-T" + i, 100.0);
            synchronized (standardTicketPool) {
                standardTicketPool.addTicket(ticket);
                logger.info("Vendor [" + id + "] released ticket: [" + ticket.getTicketID() + "]" +", Remaining tickets in pool: " + standardTicketPool.getRemainingTickets());
            }
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Vendor thread interrupted");
            }
        }
    }
}
