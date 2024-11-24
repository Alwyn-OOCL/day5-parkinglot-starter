package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ParkingBoy {

    private ParkingStategy parkingStrategy;

    private List<ParkingLot> parkingLots = new ArrayList<>();

    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        ParkingLot parkingParkingLot = getFinalParkingLot(parkingLot);
        processParkCar(parkingParkingLot, car);
        return getGenerateTicket(parkingParkingLot, car);
    }

    protected Ticket getGenerateTicket(ParkingLot parkingLot, Car car) {
        Ticket generateTicket = Ticket.generateTicket();
        parkingLot.getTicketToCarMap().put(generateTicket, car);
        return generateTicket;
    }

    protected void processParkCar(ParkingLot parkingLot, Car car) {
        parkingLot.checkCapacity();
        parkingLot.getCars().add(car);
    }

    public Car fetchCar(ParkingLot parkingLot, Ticket ticket) {
        Car car = processFetchCar(parkingLot, ticket);
        removeCarFromParkingLot(parkingLot, ticket, car);
        return car;
    }

    private Car processFetchCar(ParkingLot parkingLot, Ticket ticket) {
        ParkingLot parkingParkingLot = getFetchingParkingLot(parkingLot, ticket);
        parkingParkingLot.verifyTicket(ticket);
        return parkingParkingLot.getTicketToCarMap().get(ticket);
    }

    private void removeCarFromParkingLot(ParkingLot parkingLot, Ticket ticket, Car car) {
        parkingLot.getCars().remove(car);
        parkingLot.getTicketToCarMap().remove(ticket);
    }

    private ParkingLot getFetchingParkingLot(ParkingLot parkingLot, Ticket ticket) {
        checkIfEmptyParkingLots(parkingLot);

        if (checkIfFirstParkingLot(parkingLot)) {
            return parkingLot;
        }

        return getParkingStrategy().selectParkingLot(getParkingLots());
    }

    protected boolean checkIfFirstParkingLot(ParkingLot parkingLot) {
        return Objects.equals(parkingLots.get(0), parkingLot) && parkingLot.isAvailable();
    }

    protected void checkIfEmptyParkingLots(ParkingLot parkingLot) {
        if (parkingLots.isEmpty()) {
            parkingLots.add(parkingLot);
        }
    }

    private ParkingLot getFinalParkingLot(ParkingLot parkingLot) {
        checkIfEmptyParkingLots(parkingLot);
        if (checkIfFirstParkingLot(parkingLot)) {
            return parkingLot;
        }

        return getParkingStrategy().selectParkingLot(getParkingLots());
    }

    public ParkingBoy() {
    }

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.setParkingStrategy(new SequentialStrategy());
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public ParkingStategy getParkingStrategy() {
        return parkingStrategy;
    }

    public void setParkingStrategy(ParkingStategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }
}
