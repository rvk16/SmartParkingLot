package smartparkinglot.observer;

import smartparkinglot.model.ParkingLot;
import smartparkinglot.model.Ticket;

public interface ParkingEventObserver {
    void onVehicleEntry(Ticket ticket, ParkingLot parkingLot);
    void onVehicleExit(Ticket ticket, ParkingLot parkingLot);
}
