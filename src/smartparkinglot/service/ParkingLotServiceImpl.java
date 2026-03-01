package smartparkinglot.service;

import smartparkinglot.exception.TicketNotFoundException;
import smartparkinglot.factory.TicketFactory;
import smartparkinglot.model.*;
import smartparkinglot.strategy.SpotAllocationStrategy;

public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLot parkingLot;
    private final SpotAllocationStrategy allocationStrategy;
    private final TicketService ticketService;
    private final FeeCalculatorService feeCalculatorService;
    private final PaymentService paymentService;

    public ParkingLotServiceImpl(ParkingLot parkingLot,
                                  SpotAllocationStrategy allocationStrategy,
                                  TicketService ticketService,
                                  FeeCalculatorService feeCalculatorService,
                                  PaymentService paymentService) {
        this.parkingLot = parkingLot;
        this.allocationStrategy = allocationStrategy;
        this.ticketService = ticketService;
        this.feeCalculatorService = feeCalculatorService;
        this.paymentService = paymentService;
    }

    @Override
    public Ticket checkIn(Vehicle vehicle) {
        ParkingSpot spot = allocationStrategy.allocate(parkingLot.getFloors(), vehicle.getVehicleType());
        Ticket ticket = TicketFactory.createTicket(vehicle, spot);
        ticketService.save(ticket);
        parkingLot.notifyEntry(ticket);
        return ticket;
    }

    @Override
    public Ticket checkOut(String ticketId, PaymentMethod paymentMethod) {
        Ticket ticket = ticketService.getById(ticketId);
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new TicketNotFoundException("Ticket is not active: " + ticketId);
        }
        double fee = feeCalculatorService.calculateFee(ticket);
        ticket.checkout(fee);
        ticket.getSpot().release();
        paymentService.processPayment(ticketId, fee, paymentMethod);
        ticketService.update(ticket);
        parkingLot.notifyExit(ticket);
        return ticket;
    }

    @Override
    public long getAvailableSpots(SpotType spotType) {
        return parkingLot.getAvailableCount(spotType);
    }
}
