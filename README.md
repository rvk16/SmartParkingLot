# Smart Parking Lot — Low-Level Design

A production-quality LLD implementation for a smart parking lot backend system in Java (JDK 21).

## Features
- Multi-floor parking management with automatic spot allocation
- Vehicle check-in/check-out with entry/exit timestamping
- Real-time availability tracking via Observer pattern
- Fee calculation based on duration and vehicle type
- Thread-safe concurrency for simultaneous vehicle entry/exit

## Vehicle → Spot Type Mapping

| Vehicle | Spot Type | Fee (per hour) |
|---------|-----------|----------------|
| MOTORCYCLE | SMALL | ₹10 |
| CAR | MEDIUM | ₹20 |
| BUS | LARGE | ₹50 |

## Architecture

```
smartparkinglot/
├── manager/      ParkingLotManager       (Singleton facade)
├── model/        Vehicle, ParkingSpot, ParkingFloor, ParkingLot, Ticket, Payment
├── service/      ParkingLotService, TicketService, FeeCalculatorService, PaymentService
├── strategy/     SpotAllocationStrategy, NearestSpotAllocationStrategy
├── factory/      VehicleFactory, TicketFactory
├── observer/     ParkingEventObserver, DisplayBoardObserver
├── exception/    ParkingLotFullException, TicketNotFoundException, ...
└── util/         FeeRateConfig
```

## Design Patterns

| Pattern | Usage |
|---------|-------|
| Singleton | `ParkingLotManager` — single entry point wiring all services |
| Strategy | `SpotAllocationStrategy` — pluggable allocation algorithms |
| Factory | `VehicleFactory`, `TicketFactory` — encapsulated object creation |
| Observer | `DisplayBoardObserver` — real-time availability display |
| Service + Interface | All services backed by interfaces for testability |

## Concurrency

| Concern | Mechanism |
|---------|-----------|
| Spot allocation per floor | `ReentrantLock` per `ParkingFloor` |
| Spot occupation state | `AtomicBoolean` per `ParkingSpot` |
| Ticket store | `ConcurrentHashMap` in `TicketServiceImpl` |
| Observer list | `CopyOnWriteArrayList` in `ParkingLot` |

## Running

Open in IntelliJ IDEA (JDK 21) and run `src/smartparkinglot/Main.java`.

Or compile and run from command line:
```bash
javac -d out $(find src -name "*.java")
java -cp out smartparkinglot.Main
```

## Database Schema

```sql
CREATE TABLE vehicles (
    id            VARCHAR(36) PRIMARY KEY,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    vehicle_type  ENUM('MOTORCYCLE','CAR','BUS') NOT NULL
);

CREATE TABLE parking_spots (
    id          VARCHAR(36) PRIMARY KEY,
    floor_id    INT NOT NULL,
    spot_number INT NOT NULL,
    spot_type   ENUM('SMALL','MEDIUM','LARGE') NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    UNIQUE(floor_id, spot_number)
);

CREATE TABLE tickets (
    id          VARCHAR(36) PRIMARY KEY,
    vehicle_id  VARCHAR(36) REFERENCES vehicles(id),
    spot_id     VARCHAR(36) REFERENCES parking_spots(id),
    entry_time  TIMESTAMP NOT NULL,
    exit_time   TIMESTAMP,
    fee         DECIMAL(10,2),
    status      ENUM('ACTIVE','PAID','CANCELLED') DEFAULT 'ACTIVE'
);

CREATE TABLE payments (
    id             VARCHAR(36) PRIMARY KEY,
    ticket_id      VARCHAR(36) REFERENCES tickets(id),
    amount         DECIMAL(10,2) NOT NULL,
    payment_time   TIMESTAMP NOT NULL,
    payment_method ENUM('CASH','CARD','UPI') NOT NULL,
    status         ENUM('PENDING','SUCCESS','FAILED') DEFAULT 'SUCCESS'
);
```
