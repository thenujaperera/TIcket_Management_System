import React from "react";
import "./TicketStatus.css";

const TicketStatus = ({ ticketPool, ticketStatus }) => (
  <div className="section">
    <h2>Ticket Pool Status</h2>
    <div className="ticket-status">
      <p>Tickets Available: {ticketStatus.ticketsAvailable}</p>
      <p>Tickets Sold: {ticketStatus.ticketsSold}</p>
      <p>Tickets Released: {ticketStatus.ticketsReleased}</p>
    </div>
  </div>
);

export default TicketStatus;
