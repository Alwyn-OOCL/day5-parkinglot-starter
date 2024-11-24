package com.parkinglot;

import java.util.Comparator;
import java.util.List;

public class SuperParkingBoy extends ParkingBoy {

    public SuperParkingBoy() {
    }

    public SuperParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
        this.setParkingStrategy(new HigherPositionRateStrategy());
    }

    @Override
    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        ParkingLot parkingParkingLot = getFinalParkingLot(parkingLot);
        processParkCar(parkingParkingLot, car);
        return getGenerateTicket(parkingParkingLot, car);
    }

    public ParkingLot getFinalParkingLot(ParkingLot parkingLot) {
        super.checkIfEmptyParkingLots(parkingLot);
        return getParkingStrategy().selectParkingLot(getParkingLots());
    }

}
