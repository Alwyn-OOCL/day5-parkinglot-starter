package com.parkinglot;

public class Customer {

    private String id;

    private ParkingBoy parkingBoy;

    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        return parkingBoy.parkCar(parkingLot, car);
    }

    public Car fetchCar(ParkingLot parkingLot, Ticket ticket) {
        return parkingBoy.fetchCar(parkingLot, ticket);
    }

    public Customer() {
    }

    public Customer(ParkingBoy parkingBoy) {
        this.parkingBoy = parkingBoy;
    }

}
