package com.parkinglot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

    public static final String NOT_AVAILABLE_POSITION = "Not available position.";
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";


    @Test
    void should_return_ticket_when_park_given_parking_lot_and_car() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Customer customer = new Customer();

        // when
        Ticket ticket = customer.park(parkingLot, car);

        // then
        assertNotNull(ticket);
    }

    @Test
    void should_printMessage_when_park_given_parking_lot_and_car_and_parking_lot_is_full() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Customer customer = new Customer();

        Set<Car> cars = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            cars.add(new Car(UUID.randomUUID().toString()));
        }

        parkingLot.setCars(cars);

        // when
        Exception exception = assertThrows(Exception.class, () -> customer.park(parkingLot, car));

        // then
        assertEquals(NOT_AVAILABLE_POSITION, exception.getMessage());
    }


    @Test
    void should_return_car_when_fetch_given_parking_lot_and_ticket() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Customer customer = new Customer();
        Ticket ticket = customer.park(parkingLot, car);

        // when
        Car fetchedCar = customer.fetch(parkingLot, ticket);

        // then
        assertNotNull(fetchedCar);
        assertFalse(parkingLot.getCars().contains(fetchedCar));
        assertFalse(parkingLot.getTicketToCarMap().containsKey(ticket));
    }

    @Test
    void should_print_errorMessage_when_fetch_given_wrong_ticket() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Customer customer = new Customer();
        Ticket ticket = customer.park(parkingLot, car);
        Ticket wrongTicket = new Ticket(UUID.randomUUID().toString());

        // when
        Exception exception = assertThrows(Exception.class, () -> customer.fetch(parkingLot, wrongTicket));

        // then
        assertEquals(NOT_AVAILABLE_POSITION, exception.getMessage());
    }

    @Test
    void should_print_errorMessage_when_fetch_given_usedTicket() {
        // given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Customer customer = new Customer();
        Ticket ticket = customer.park(parkingLot, car);

        // when
        customer.fetch(parkingLot, ticket);
        Exception exception = assertThrows(Exception.class, () -> customer.fetch(parkingLot, ticket));

        // then
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception.getMessage());
    }


}
