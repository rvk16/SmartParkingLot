package smartparkinglot.manager;

import smartparkinglot.model.*;
import smartparkinglot.observer.DisplayBoardObserver;
import smartparkinglot.service.*;
import smartparkinglot.strategy.NearestSpotAllocationStrategy;
import smartparkinglot.strategy.SpotAllocationStrategy;

public class ParkingLotManager {
    private static volatile ParkingLotManager instance;

    private final ParkingLotService parkingLotService;
    private final ParkingLot parkingLot;

    private ParkingLotManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;

        TicketService ticketService = new TicketServiceImpl();
        FeeCalculatorService feeCalculatorService = new FeeCalculatorServiceImpl();
        PaymentService paymentService = new PaymentServiceImpl();
        SpotAllocationStrategy allocationStrategy = new NearestSpotAllocationStrategy();

        this.parkingLotService = new ParkingLotServiceImpl(
                parkingLot, allocationStrategy, ticketService, feeCalculatorService, paymentService
        );

        parkingLot.registerObserver(new DisplayBoardObserver());
    }

    public static ParkingLotManager getInstance(ParkingLot parkingLot) {
        if (instance == null) {
            synchronized (ParkingLotManager.class) {
                if (instance == null) {
                    instance = new ParkingLotManager(parkingLot);
                }
            }
        }
        return instance;
    }

    public Ticket checkIn(Vehicle vehicle) {
        return parkingLotService.checkIn(vehicle);
    }

    public Ticket checkOut(String ticketId, PaymentMethod paymentMethod) {
        return parkingLotService.checkOut(ticketId, paymentMethod);
    }

    public long getAvailableSpots(SpotType spotType) {
        return parkingLotService.getAvailableSpots(spotType);
    }
}
