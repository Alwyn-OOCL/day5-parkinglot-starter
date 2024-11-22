package com.parkinglot;

public class Customer {

    public Ticket park(ParkingLot parkingLot, Car car) {
        return new Ticket();
    }

    public Car fetch(ParkingLot parkingLot, Ticket ticket) {
        return new Car();
    }
}
