package com.parkinglot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

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

    //    @Test
//    void should_printMessage_when_park_given_parking_lot_and_car_and_parking_lot_is_full() {
//        // given
//        ParkingLot parkingLot = new ParkingLot();
//        Car car = new Car();
//        Customer customer = new Customer();
//
//        // when
//        customer.park(car);
//
//        // then
//        assertThat(systemOut()).contains("Not enough position.");
//    }
//
//
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
    }
//
//    @Test
//    void should_print_errorMessage_when_fetch_given_wrong_ticket() {
//        // given
//        ParkingLot parkingLot = new ParkingLot();
//        Car car = new Car();
//        Customer customer = new Customer();
//        Ticket ticket = customer.park(car);
//        Ticket wrongTicket = new Ticket();
//
//        // when
//        Car fetchedCar = customer.fetch(wrongTicket);
//
//        // then
//        assertNull(fetchedCar);
//        assertThat(systemOut()).contains("Unrecognized parking ticket.");
//    }
//
//    @Test
//    void should_print_errorMessage_when_fetch_given_usedTicket() {
//        // given
//        ParkingLot parkingLot = new ParkingLot();
//        Car car = new Car();
//        Customer customer = new Customer();
//        Ticket ticket = customer.park(car);
//
//        // when
//        Car fetchedCar = customer.fetch(null);
//
//        Ticket usedTicket = new Ticket();
//
//        // then
//        assertNull(fetchedCar);
//        assertThat(systemOut()).contains("Unrecognized parking ticket.");
//    }

    private String systemOut() {
        return outContent.toString();
    }

}
