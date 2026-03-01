package smartparkinglot.service;

import smartparkinglot.model.Payment;
import smartparkinglot.model.PaymentMethod;

import java.util.concurrent.ConcurrentHashMap;

public class PaymentServiceImpl implements PaymentService {
    private final ConcurrentHashMap<String, Payment> paymentStore = new ConcurrentHashMap<>();

    @Override
    public Payment processPayment(String ticketId, double amount, PaymentMethod method) {
        Payment payment = new Payment(ticketId, amount, method);
        paymentStore.put(payment.getId(), payment);
        return payment;
    }
}
