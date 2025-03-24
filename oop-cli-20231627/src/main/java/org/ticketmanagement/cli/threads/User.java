package org.ticketmanagement.cli.threads;

import org.ticketmanagement.cli.tickets.StandardTicketPool;

/**
 * Model of a user
 * Represents a user in the ticket management system (either a vendor or a customer).
 */
public abstract class User implements Runnable {
    protected String id;
    protected StandardTicketPool standardTicketPool;

    /**
     * Constructor to initialize a user.
     * @param id The unique ID of the user.
     * @param standardTicketPool The ticket pool associated with the user.
     */
    public User(String id, StandardTicketPool standardTicketPool) {
        this.id = id;
        this.standardTicketPool = standardTicketPool;
    }

    /**
     * Abstract method to define the logic of the user.
     */
    public abstract void run();
}
