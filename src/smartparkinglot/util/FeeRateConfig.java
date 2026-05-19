package smartparkinglot.util;

import smartparkinglot.model.VehicleType;

import java.util.EnumMap;
import java.util.Map;

public class FeeRateConfig {
    private static final Map<VehicleType, Double> HOURLY_RATES = new EnumMap<>(VehicleType.class);

    static {
        HOURLY_RATES.put(VehicleType.MOTORCYCLE, 10.0);
        HOURLY_RATES.put(VehicleType.CAR, 20.0);
        HOURLY_RATES.put(VehicleType.BUS, 50.0);
    }

    public static double getHourlyRate(VehicleType vehicleType) {
        return HOURLY_RATES.getOrDefault(vehicleType, 0.0);
    }

    private FeeRateConfig() {}
}
