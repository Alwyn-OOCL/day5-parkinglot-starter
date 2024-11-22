package com.parkinglot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParkingLot {

    private static final int DEFAULT_CAPACITY = 10;


    private Set<Car> cars = new HashSet<>(DEFAULT_CAPACITY);
    private Map<Ticket, Car> ticketToCarMap = new HashMap<>();


    public Set<Car> getCars() {
        return cars;
    }
    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Map<Ticket, Car> getTicketToCarMap() {
        return ticketToCarMap;
    }

    public void setTicketToCarMap(Map<Ticket, Car> ticketToCarMap) {
        this.ticketToCarMap = ticketToCarMap;
    }

    public boolean checkCapacity() {
        if (cars.size() >= DEFAULT_CAPACITY) {
            System.out.println("Not available position.");
            return false;
        }
        return true;
    }

    public boolean verifyTicket(Ticket ticket) {
        if (!ticketToCarMap.containsKey(ticket)) {
            System.out.println("Unrecognized parking ticket.");
            return false;
        }
        return true;
    }
}
