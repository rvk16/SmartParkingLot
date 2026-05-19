package smartparkinglot.model;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingSpot {
    private final String id;
    private final int floorId;
    private final int spotNumber;
    private final SpotType spotType;
    private final AtomicBoolean isOccupied;

    public ParkingSpot(int floorId, int spotNumber, SpotType spotType) {
        this.id = UUID.randomUUID().toString();
        this.floorId = floorId;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.isOccupied = new AtomicBoolean(false);
    }

    public boolean occupy() {
        return isOccupied.compareAndSet(false, true);
    }

    public void release() {
        isOccupied.set(false);
    }

    public boolean isAvailable() {
        return !isOccupied.get();
    }

    public String getId() { return id; }
    public int getFloorId() { return floorId; }
    public int getSpotNumber() { return spotNumber; }
    public SpotType getSpotType() { return spotType; }

    @Override
    public String toString() {
        return "ParkingSpot{floor=" + floorId + ", spot=" + spotNumber + ", type=" + spotType + ", occupied=" + isOccupied.get() + "}";
    }
}
