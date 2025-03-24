package com.thenuja.oopcw.dto;

/**
 * Data Transfer Object (DTO) for ticket statistics.
 */
public class TicketStatisticsDTO {
    private int ticketsAvailable;
    private int ticketsSold;
    private int ticketsReleased;
    /**
     * Constructor to initialize ticket statistics.
     * @param ticketsAvailable the number of tickets available in the pool.
     * @param ticketsSold the number of tickets sold.
     * @param ticketsReleased the number of tickets released.
     */
    public TicketStatisticsDTO(int ticketsAvailable, int ticketsSold, int ticketsReleased) {
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
        this.ticketsReleased = ticketsReleased;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public int getTicketsReleased() {
        return ticketsReleased;
    }
}
