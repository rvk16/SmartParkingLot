package smartparkinglot.factory;

import smartparkinglot.model.ParkingSpot;
import smartparkinglot.model.Ticket;
import smartparkinglot.model.Vehicle;

public class TicketFactory {
    public static Ticket createTicket(Vehicle vehicle, ParkingSpot spot) {
        return new Ticket(vehicle, spot);
    }

    private TicketFactory() {}
}
