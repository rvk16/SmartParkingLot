package smartparkinglot.service;

import smartparkinglot.exception.TicketNotFoundException;
import smartparkinglot.model.Ticket;

import java.util.concurrent.ConcurrentHashMap;

public class TicketServiceImpl implements TicketService {
    private final ConcurrentHashMap<String, Ticket> ticketStore = new ConcurrentHashMap<>();

    @Override
    public void save(Ticket ticket) {
        ticketStore.put(ticket.getId(), ticket);
    }

    @Override
    public Ticket getById(String ticketId) {
        Ticket ticket = ticketStore.get(ticketId);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found: " + ticketId);
        }
        return ticket;
    }

    @Override
    public void update(Ticket ticket) {
        if (!ticketStore.containsKey(ticket.getId())) {
            throw new TicketNotFoundException("Cannot update unknown ticket: " + ticket.getId());
        }
        ticketStore.put(ticket.getId(), ticket);
    }
}
