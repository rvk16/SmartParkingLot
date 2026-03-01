package smartparkinglot.model;

import java.util.UUID;

public class Vehicle {
    private final String id;
    private final String licensePlate;
    private final VehicleType vehicleType;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        this.id = UUID.randomUUID().toString();
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public String getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }

    @Override
    public String toString() {
        return "Vehicle{licensePlate='" + licensePlate + "', type=" + vehicleType + "}";
    }
}
