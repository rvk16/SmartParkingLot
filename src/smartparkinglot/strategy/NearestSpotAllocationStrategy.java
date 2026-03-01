package smartparkinglot.strategy;

import smartparkinglot.exception.ParkingLotFullException;
import smartparkinglot.exception.InvalidVehicleTypeException;
import smartparkinglot.model.ParkingFloor;
import smartparkinglot.model.ParkingSpot;
import smartparkinglot.model.SpotType;
import smartparkinglot.model.VehicleType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class NearestSpotAllocationStrategy implements SpotAllocationStrategy {
    private static final Map<VehicleType, SpotType> VEHICLE_TO_SPOT = new EnumMap<>(VehicleType.class);

    static {
        VEHICLE_TO_SPOT.put(VehicleType.MOTORCYCLE, SpotType.SMALL);
        VEHICLE_TO_SPOT.put(VehicleType.CAR, SpotType.MEDIUM);
        VEHICLE_TO_SPOT.put(VehicleType.BUS, SpotType.LARGE);
    }

    @Override
    public ParkingSpot allocate(List<ParkingFloor> floors, VehicleType vehicleType) {
        SpotType required = VEHICLE_TO_SPOT.get(vehicleType);
        if (required == null) {
            throw new InvalidVehicleTypeException("Unsupported vehicle type: " + vehicleType);
        }

        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.allocateSpot(required);
            if (spot != null) {
                return spot;
            }
        }

        throw new ParkingLotFullException("No available " + required + " spot for vehicle type: " + vehicleType);
    }
}
