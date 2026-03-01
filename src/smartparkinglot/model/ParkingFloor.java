package smartparkinglot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingFloor {
    private final int floorId;
    private final List<ParkingSpot> spots;
    private final Map<SpotType, List<ParkingSpot>> spotsByType;
    private final ReentrantLock lock;

    public ParkingFloor(int floorId) {
        this.floorId = floorId;
        this.spots = new ArrayList<>();
        this.spotsByType = new EnumMap<>(SpotType.class);
        for (SpotType type : SpotType.values()) {
            spotsByType.put(type, new ArrayList<>());
        }
        this.lock = new ReentrantLock();
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
        spotsByType.get(spot.getSpotType()).add(spot);
    }

    public ParkingSpot allocateSpot(SpotType spotType) {
        lock.lock();
        try {
            for (ParkingSpot spot : spotsByType.getOrDefault(spotType, Collections.emptyList())) {
                if (spot.occupy()) {
                    return spot;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void releaseSpot(ParkingSpot spot) {
        lock.lock();
        try {
            spot.release();
        } finally {
            lock.unlock();
        }
    }

    public long getAvailableCount(SpotType spotType) {
        return spotsByType.getOrDefault(spotType, Collections.emptyList())
                .stream()
                .filter(ParkingSpot::isAvailable)
                .count();
    }

    public int getFloorId() { return floorId; }
    public List<ParkingSpot> getSpots() { return Collections.unmodifiableList(spots); }
    public Map<SpotType, List<ParkingSpot>> getSpotsByType() { return Collections.unmodifiableMap(spotsByType); }
}
