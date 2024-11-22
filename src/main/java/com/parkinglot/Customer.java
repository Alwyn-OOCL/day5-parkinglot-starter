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
        if (!parkingLot.checkCapacity()) {
            return null;
        }
        parkingLot.getCars().add(car);
        Ticket generateTicket = Ticket.generateTicket();
        parkingLot.getTicketToCarMap().put(generateTicket, car);
        return generateTicket;
    }

    public Car fetch(ParkingLot parkingLot, Ticket ticket) {
        if (!parkingLot.verifyTicket(ticket)) {
            return null;
        }
        Car car = parkingLot.getTicketToCarMap().get(ticket);
        parkingLot.getCars().remove(car);
        parkingLot.getTicketToCarMap().remove(ticket);
        return car;
    }
}
