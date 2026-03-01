package smartparkinglot.model;

import smartparkinglot.observer.ParkingEventObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private final String id;
    private final String name;
    private final String address;
    private final List<ParkingFloor> floors;
    private final List<ParkingEventObserver> observers;

    public ParkingLot(String name, String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.floors = new ArrayList<>();
        this.observers = new CopyOnWriteArrayList<>();
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void registerObserver(ParkingEventObserver observer) {
        observers.add(observer);
    }

    public void notifyEntry(Ticket ticket) {
        for (ParkingEventObserver observer : observers) {
            observer.onVehicleEntry(ticket, this);
        }
    }

    public void notifyExit(Ticket ticket) {
        for (ParkingEventObserver observer : observers) {
            observer.onVehicleExit(ticket, this);
        }
    }

    public long getAvailableCount(SpotType spotType) {
        return floors.stream()
                .mapToLong(floor -> floor.getAvailableCount(spotType))
                .sum();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<ParkingFloor> getFloors() { return Collections.unmodifiableList(floors); }
}
