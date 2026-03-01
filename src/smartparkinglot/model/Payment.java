package smartparkinglot.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final String id;
    private final String ticketId;
    private final double amount;
    private final LocalDateTime paymentTime;
    private final PaymentMethod method;
    private final String status;

    public Payment(String ticketId, double amount, PaymentMethod method) {
        this.id = UUID.randomUUID().toString();
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
        this.method = method;
        this.status = "SUCCESS";
    }

    public String getId() { return id; }
    public String getTicketId() { return ticketId; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public PaymentMethod getMethod() { return method; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Payment{id='" + id + "', ticketId='" + ticketId
                + "', amount=" + amount + ", method=" + method + ", status=" + status + "}";
    }
}
