package com.parkinglot;

import java.util.Comparator;
import java.util.List;

public class HigherPositionRateStrategy implements ParkingStategy {
    @Override
    public ParkingLot selectParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .filter(ParkingLot::isAvailable)
                .max(Comparator.comparingDouble(ParkingLot::getPositionRate))
                .orElseThrow(() -> new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION));
    }
}
