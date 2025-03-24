package com.thenuja.oopcw.tickets;
/**
 * StandardTicketPool class representing the pool where tickets are added and bought.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class StandardTicketPool implements TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maxCapacity;
    private int ticketsReleased = 0;
    private int ticketsSold = 0;
    private static final Logger logger = Logger.getLogger(StandardTicketPool.class.getName());

    public StandardTicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds a ticket to the pool.
     * @param ticket the ticket to add.
     */
    @Override
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity) {
            try {
                logger.info("TicketPool full. Vendor thread will wait.");
                wait();
            } catch (InterruptedException e) {
                logger.warning("Vendor thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
        ticketQueue.add(ticket);
        ticketsReleased++;
        //logger.info("Ticket added to the pool. Current size: " + ticketQueue.size());
        //System.out.println("Logg::Ticket added to pool. Current size: " + ticketQueue.size());
        notifyAll();
    }

    /**
     * Buys a ticket from the pool.
     * @return the ticket bought or null if no tickets are available.
     */
    @Override
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                logger.info("TicketPool empty. Customer thread will wait for ticket to be released ....");
                //System.out.println("Logg::Waiting for ticket to be released ... (no more tickets)");
                wait();
            } catch (InterruptedException e) {
                //.warning("Customer thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = ticketQueue.poll();
        ticketsSold++;
        //logger.info("Ticket removed from the pool. Current size: " + ticketQueue.size());
        //System.out.println("Logg::Ticket bought from pool. Current size: " + ticketQueue.size());
        notifyAll();
        return ticket;
    }

    /**
     * returns ticket pool size in a synchronized way.
     * @return Returns the remaining tickets in the pool
     */
    @Override
    public synchronized int getRemainingTickets() {
        int size = ticketQueue.size();
        //System.out.println("Current ticket pool size: " + size);
        return size;
    }
    public synchronized int getTicketsSold() {
        return ticketsSold;
    }
    public synchronized int getTicketsReleased() {
        return ticketsReleased;
    }
}
