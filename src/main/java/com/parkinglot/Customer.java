package com.parkinglot;

public class Customer {

    public Ticket park(Car car) {
        return new Ticket();
    }

    public Car fetch(Ticket ticket) {
        return new Car();
    }
}
