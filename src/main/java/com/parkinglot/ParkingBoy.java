package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ParkingBoy {

    private String id;

    private List<ParkingLot> parkingLots = new ArrayList<>();

    public Ticket parkCar(ParkingLot parkingLot, Car car) {
        ParkingLot parkingParkingLot = getParkingParkingLot(parkingLot);
        processParkCar(parkingParkingLot, car);
        return getGenerateTicket(parkingParkingLot, car);
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

    private ParkingLot getParkingParkingLot(ParkingLot parkingLot) {
        if (parkingLots.isEmpty()) {
            throw new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION);
        }

        if (Objects.equals(parkingLots.get(0), parkingLot) && parkingLot.isAvailable()) {
            return parkingLot;
        }

        int parkingLotIndex = IntStream.range(0, parkingLots.size())
                .filter(i -> parkingLots.get(i).isAvailable()).findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLot.NOT_AVAILABLE_POSITION));

        return parkingLots.get(parkingLotIndex);
    }

    public ParkingBoy() {
    }

    public ParkingBoy(String id) {
        this.id = id;
    }

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }
}
