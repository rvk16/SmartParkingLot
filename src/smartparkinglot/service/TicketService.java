package smartparkinglot.service;

import smartparkinglot.model.Ticket;

public interface TicketService {
    void save(Ticket ticket);
    Ticket getById(String ticketId);
    void update(Ticket ticket);
}
