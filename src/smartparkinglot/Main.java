package smartparkinglot;

import smartparkinglot.exception.ParkingLotFullException;
import smartparkinglot.factory.VehicleFactory;
import smartparkinglot.manager.ParkingLotManager;
import smartparkinglot.model.*;

public class Main {

    public static void main(String[] args) {
        // --- Setup parking lot: 3 floors, each with 5 SMALL, 5 MEDIUM, 2 LARGE ---
        ParkingLot lot = new ParkingLot("City Center Parking", "123 MG Road, Pune");

        for (int f = 1; f <= 3; f++) {
            ParkingFloor floor = new ParkingFloor(f);
            int spotNum = 1;
            for (int i = 0; i < 5; i++) floor.addSpot(new ParkingSpot(f, spotNum++, SpotType.SMALL));
            for (int i = 0; i < 5; i++) floor.addSpot(new ParkingSpot(f, spotNum++, SpotType.MEDIUM));
            for (int i = 0; i < 2; i++) floor.addSpot(new ParkingSpot(f, spotNum++, SpotType.LARGE));
            lot.addFloor(floor);
        }

        ParkingLotManager manager = ParkingLotManager.getInstance(lot);

        System.out.println("=== Smart Parking Lot: " + lot.getName() + " ===");

        // --- Check-in ---
        Vehicle motorcycle = VehicleFactory.createVehicle("MH01AB1234", VehicleType.MOTORCYCLE);
        Vehicle car        = VehicleFactory.createVehicle("MH02CD5678", VehicleType.CAR);
        Vehicle bus        = VehicleFactory.createVehicle("MH03EF9012", VehicleType.BUS);

        Ticket motoTicket = manager.checkIn(motorcycle);
        Ticket carTicket  = manager.checkIn(car);
        Ticket busTicket  = manager.checkIn(bus);

        // --- Check-out the car ---
        System.out.println("\n--- Checking out: " + car.getLicensePlate() + " ---");
        Ticket completedTicket = manager.checkOut(carTicket.getId(), PaymentMethod.UPI);
        System.out.printf("Payment completed. Total fee: Rs. %.2f%n", completedTicket.getFee());

        // --- Demonstrate overflow protection ---
        System.out.println("\n--- Filling up all BUS spots (2 per floor × 3 floors = 6 total) ---");
        try {
            for (int i = 0; i < 7; i++) {
                Vehicle extraBus = VehicleFactory.createVehicle("BUS" + String.format("%03d", i), VehicleType.BUS);
                manager.checkIn(extraBus);
                System.out.println("  Bus #" + i + " checked in.");
            }
        } catch (ParkingLotFullException e) {
            System.out.println("\n[EXCEPTION CAUGHT] " + e.getMessage());
        }

        // --- Final availability summary ---
        System.out.println("\n=== Final Availability ===");
        for (SpotType type : SpotType.values()) {
            System.out.println("  " + type + ": " + manager.getAvailableSpots(type) + " spots available");
        }
    }
}
