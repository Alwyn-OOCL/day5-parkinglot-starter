package com.parkinglot;

import java.util.List;

public class SmartParkingBoy extends ParkingBoy {


    public SmartParkingBoy() {
    }

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
        this.setParkingStrategy(new MorePositionStategy());
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
