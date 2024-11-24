package com.parkinglot;

import java.util.Comparator;
import java.util.List;

public class MorePositionStategy implements ParkingStategy {
    @Override
    public ParkingLot selectParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .filter(ParkingLot::isAvailable)
                .min(Comparator.comparingInt(lot -> lot.getCars().size()))
                .orElseThrow(() -> new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION));
    }
}
