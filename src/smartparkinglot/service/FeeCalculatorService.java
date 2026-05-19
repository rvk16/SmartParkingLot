package smartparkinglot.service;

import smartparkinglot.model.Ticket;

public interface FeeCalculatorService {
    double calculateFee(Ticket ticket);
}
