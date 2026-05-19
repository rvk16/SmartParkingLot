package smartparkinglot.service;

import smartparkinglot.model.Payment;
import smartparkinglot.model.PaymentMethod;

public interface PaymentService {
    Payment processPayment(String ticketId, double amount, PaymentMethod method);
}
