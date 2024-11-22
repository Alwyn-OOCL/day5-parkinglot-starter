package com.parkinglot;

import java.util.Set;

public class Customer {

    private String id;

    public Customer() {
    }

    public Customer(String id) {
        this.id = id;
    }

    public Ticket park(ParkingLot parkingLot, Car car) {
        parkingLot.checkCapacity();
        parkingLot.getCars().add(car);
        Ticket generateTicket = Ticket.generateTicket();
        parkingLot.getTicketToCarMap().put(generateTicket, car);
        return generateTicket;
    }

    public Car fetch(ParkingLot parkingLot, Ticket ticket) {
        parkingLot.verifyTicket(ticket);
        Car car = parkingLot.getTicketToCarMap().get(ticket);
        parkingLot.getCars().remove(car);
        parkingLot.getTicketToCarMap().remove(ticket);
        return car;
    }
}
