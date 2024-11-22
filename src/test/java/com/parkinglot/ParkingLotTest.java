package com.parkinglot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ParkingLotTest {

    public static final String NOT_AVAILABLE_POSITION = "Not available position.";
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";

    @Test
    void should_return_ticket_when_park_given_parking_lot_and_car_and_parkingBoy() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy();
        Customer customer = new Customer(parkingBoy);

        // when
        Ticket ticket = customer.parkCar(parkingLot, car);

        // then
        assertNotNull(ticket);
    }

    @Test
    void should_printMessage_when_park_given_parking_lot_and_car_and_parkingBoy_and_parking_lot_is_full() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy();
        Customer customer = new Customer(parkingBoy);

        Set<Car> cars = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            cars.add(new Car(UUID.randomUUID().toString()));
        }

        parkingLot.setCars(cars);

        // when
        ParkingLotException exception = assertThrows(ParkingLotException.class, () -> customer.parkCar(parkingLot, car));

        // then
        assertEquals(NOT_AVAILABLE_POSITION, exception.getMessage());
    }


    @Test
    void should_return_car_when_fetch_given_parking_lot_and_ticket_and_parkingBoy() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy();
        Customer customer = new Customer(parkingBoy);
        Ticket ticket = customer.parkCar(parkingLot, car);

        // when
        Car fetchedCar = customer.fetchCar(parkingLot, ticket);

        // then
        assertNotNull(fetchedCar);
        assertFalse(parkingLot.getCars().contains(fetchedCar));
        assertFalse(parkingLot.getTicketToCarMap().containsKey(ticket));
    }

    @Test
    void should_print_errorMessage_when_fetch_given_wrong_ticket_and_parkingBoy() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy();
        Customer customer = new Customer(parkingBoy);
        customer.parkCar(parkingLot, car);
        Ticket wrongTicket = new Ticket(UUID.randomUUID().toString());

        // when
        ParkingLotException exception = assertThrows(ParkingLotException.class, () -> customer.fetchCar(parkingLot, wrongTicket));

        // then
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception.getMessage());
    }

    @Test
    void should_print_errorMessage_when_fetch_given_usedTicket_and_parkingBoy() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy();
        Customer customer = new Customer(parkingBoy);
        Ticket ticket = customer.parkCar(parkingLot, car);

        // when
        customer.fetchCar(parkingLot, ticket);
        ParkingLotException exception = assertThrows(ParkingLotException.class, () -> customer.fetchCar(parkingLot, ticket));

        // then
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception.getMessage());
    }


}
