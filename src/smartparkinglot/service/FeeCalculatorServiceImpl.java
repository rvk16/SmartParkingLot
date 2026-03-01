package smartparkinglot.service;

import smartparkinglot.model.Ticket;
import smartparkinglot.util.FeeRateConfig;

import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculatorServiceImpl implements FeeCalculatorService {

    @Override
    public double calculateFee(Ticket ticket) {
        LocalDateTime entry = ticket.getEntryTime();
        LocalDateTime exit = LocalDateTime.now();
        long minutes = Duration.between(entry, exit).toMinutes();
        long billableHours = (long) Math.ceil(minutes / 60.0);
        if (billableHours < 1) billableHours = 1;
        double rate = FeeRateConfig.getHourlyRate(ticket.getVehicle().getVehicleType());
        return billableHours * rate;
    }
}
