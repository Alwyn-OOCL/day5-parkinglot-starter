package com.parkinglot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParkingLot {

    private static final int DEFAULT_CAPACITY = 10;
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";
    public static final String NOT_AVAILABLE_POSITION = "Not available position.";

    private Integer totalCapacity = DEFAULT_CAPACITY;


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

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void checkCapacity() {
        if (cars.size() >= DEFAULT_CAPACITY) {
            throw new ParkingLotException(NOT_AVAILABLE_POSITION);
        }
    }

    public void verifyTicket(Ticket ticket) {
        if (!ticketToCarMap.containsKey(ticket)) {
            throw new ParkingLotException(UNRECOGNIZED_PARKING_TICKET);
        }
    }

    public boolean isAvailable() {
        return cars.size() < DEFAULT_CAPACITY;
    }

    public Integer getPositionRate() {
        return (DEFAULT_CAPACITY - cars.size()) / DEFAULT_CAPACITY;
    }
}
