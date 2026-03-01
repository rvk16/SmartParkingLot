package smartparkinglot.observer;

import smartparkinglot.model.ParkingLot;
import smartparkinglot.model.SpotType;
import smartparkinglot.model.Ticket;

public class DisplayBoardObserver implements ParkingEventObserver {

    @Override
    public void onVehicleEntry(Ticket ticket, ParkingLot parkingLot) {
        System.out.println("\n[DISPLAY BOARD] Vehicle ENTERED: " + ticket.getVehicle().getLicensePlate());
        System.out.println("  Assigned: Floor " + ticket.getSpot().getFloorId()
                + ", Spot #" + ticket.getSpot().getSpotNumber()
                + " (" + ticket.getSpot().getSpotType() + ")");
        printAvailability(parkingLot);
    }

    @Override
    public void onVehicleExit(Ticket ticket, ParkingLot parkingLot) {
        System.out.println("\n[DISPLAY BOARD] Vehicle EXITED: " + ticket.getVehicle().getLicensePlate());
        System.out.printf("  Fee charged: Rs. %.2f%n", ticket.getFee());
        printAvailability(parkingLot);
    }

    private void printAvailability(ParkingLot parkingLot) {
        System.out.println("  -- Real-time Availability --");
        for (SpotType type : SpotType.values()) {
            System.out.println("  " + type + ": " + parkingLot.getAvailableCount(type) + " spots available");
        }
    }
}
