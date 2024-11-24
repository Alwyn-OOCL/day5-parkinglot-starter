package com.parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SequentialStrategy implements ParkingStategy {

    @Override
    public ParkingLot selectParkingLot(List<ParkingLot> parkingLots) {
        return IntStream.range(0, parkingLots.size())
                .filter(i -> parkingLots.get(i).isAvailable())
                .mapToObj(parkingLots::get)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION));
    }

}
