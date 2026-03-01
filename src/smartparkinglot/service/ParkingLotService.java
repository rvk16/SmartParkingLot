package smartparkinglot.service;

import smartparkinglot.model.PaymentMethod;
import smartparkinglot.model.SpotType;
import smartparkinglot.model.Ticket;
import smartparkinglot.model.Vehicle;

public interface ParkingLotService {
    Ticket checkIn(Vehicle vehicle);
    Ticket checkOut(String ticketId, PaymentMethod paymentMethod);
    long getAvailableSpots(SpotType spotType);
}
