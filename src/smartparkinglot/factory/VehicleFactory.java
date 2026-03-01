package smartparkinglot.factory;

import smartparkinglot.model.Vehicle;
import smartparkinglot.model.VehicleType;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be empty");
        }
        return new Vehicle(licensePlate.toUpperCase(), vehicleType);
    }

    private VehicleFactory() {}
}
