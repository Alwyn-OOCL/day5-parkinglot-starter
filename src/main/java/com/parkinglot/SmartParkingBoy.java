package com.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmartParkingBoy extends ParkingBoy {

    private List<ParkingLot> parkingLots = new ArrayList<>();

    public SmartParkingBoy() {
    }

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        ParkingLot parkingParkingLot = getFinalParkingLot(parkingLot);
        processParkCar(parkingParkingLot, car);
        return getGenerateTicket(parkingParkingLot, car);
    }

    public ParkingLot getFinalParkingLot(ParkingLot parkingLot) {
        super.checkIfEmptyParkingLots(parkingLot);
        return getParkingLots().stream()
                .filter(ParkingLot::isAvailable)
                .min(Comparator.comparingInt(lot -> lot.getCars().size()))
                .orElseThrow(() -> new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION));
    }

}
