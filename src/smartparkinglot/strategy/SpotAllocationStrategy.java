package smartparkinglot.strategy;

import smartparkinglot.model.ParkingFloor;
import smartparkinglot.model.ParkingSpot;
import smartparkinglot.model.VehicleType;

import java.util.List;

public interface SpotAllocationStrategy {
    ParkingSpot allocate(List<ParkingFloor> floors, VehicleType vehicleType);
}
