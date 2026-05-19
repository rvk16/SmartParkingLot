package smartparkinglot.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String id;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;
    private TicketStatus status;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.id = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    public void checkout(double fee) {
        this.exitTime = LocalDateTime.now();
        this.fee = fee;
        this.status = TicketStatus.PAID;
    }

    public String getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getFee() { return fee; }
    public TicketStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Ticket{id='" + id + "', vehicle=" + vehicle.getLicensePlate()
                + ", spot=" + spot + ", entry=" + entryTime + ", status=" + status + "}";
    }
}
