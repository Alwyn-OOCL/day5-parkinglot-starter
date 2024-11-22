package com.parkinglot;

public class ParkingBoy {

    private String id;

    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        processParkCar(parkingLot, car);
        return getGenerateTicket(parkingLot, car);
    }

    private Ticket getGenerateTicket(ParkingLot parkingLot, Car car) {
        Ticket generateTicket = Ticket.generateTicket();
        parkingLot.getTicketToCarMap().put(generateTicket, car);
        return generateTicket;
    }

    private void processParkCar(ParkingLot parkingLot, Car car) {
        parkingLot.checkCapacity();
        parkingLot.getCars().add(car);
    }

    public Car fetchCar(ParkingLot parkingLot, Ticket ticket) {
        Car car = processFetchCar(parkingLot, ticket);
        removeCarFromParkingLot(parkingLot, ticket, car);
        return car;
    }

    private Car processFetchCar(ParkingLot parkingLot, Ticket ticket) {
        parkingLot.verifyTicket(ticket);
        return parkingLot.getTicketToCarMap().get(ticket);
    }

    private void removeCarFromParkingLot(ParkingLot parkingLot, Ticket ticket, Car car) {
        parkingLot.getCars().remove(car);
        parkingLot.getTicketToCarMap().remove(ticket);
    }

    public ParkingBoy() {
    }

    public ParkingBoy(String id) {
        this.id = id;
    }
}
