package com.parkinglot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Test
    void should_park_in_first_parking_lot_when_park_given_2_parkingLots_and_car_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Customer customer = new Customer(parkingBoy);

        // when
        Ticket ticket = customer.parkCar(parkingLot2, car);

        // then
        assertNotNull(ticket);
        assertEquals(1, parkingLot1.getCars().size());
        assertEquals(0, parkingLot2.getCars().size());
    }

    @Test
    void should_park_in_second_parking_lot_when_park_given_2_parkingLots_and_first_parking_lot_is_full_and_car_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Customer customer = new Customer(parkingBoy);

        Set<Car> cars = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            cars.add(new Car(UUID.randomUUID().toString()));
        }

        parkingLot1.setCars(cars);

        // when
        Ticket ticket = customer.parkCar(parkingLot1, car);

        // then
        assertNotNull(ticket);
        assertEquals(10, parkingLot1.getCars().size());
        assertEquals(1, parkingLot2.getCars().size());
    }

    @Test
    void should_return_2_car_when_fetch_given_2_parkingLots_and_2_ticket_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Customer customer = new Customer(parkingBoy);
        Ticket ticket1 = customer.parkCar(parkingLot1, car1);
        Ticket ticket2 = customer.parkCar(parkingLot2, car2);

        // when
        Car fetchedCar1 = customer.fetchCar(parkingLot1, ticket1);
        Car fetchedCar2 = customer.fetchCar(parkingLot2, ticket2);

        // then
        assertNotNull(fetchedCar1);
        assertNotNull(fetchedCar2);
        assertFalse(parkingLot1.getCars().contains(fetchedCar1));
        assertFalse(parkingLot2.getCars().contains(fetchedCar2));
        assertFalse(parkingLot1.getTicketToCarMap().containsKey(ticket1));
        assertFalse(parkingLot2.getTicketToCarMap().containsKey(ticket2));
        assertEquals(car1, fetchedCar1);
        assertEquals(car2, fetchedCar2);
    }

    @Test
    void should_print_errorMessage_when_fetch_given_wrong_ticket_and_2_parkingLots_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Customer customer = new Customer(parkingBoy);
        customer.parkCar(parkingLot1, car1);
        customer.parkCar(parkingLot2, car2);
        Ticket wrongTicket = new Ticket(UUID.randomUUID().toString());

        // when
        ParkingLotException exception = assertThrows(ParkingLotException.class, () -> customer.fetchCar(parkingLot1, wrongTicket));

        // then
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception.getMessage());
    }

    @Test
    void should_print_errorMessage_when_fetch_given_usedTicket_and_2_parkingLots_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2));
        Customer customer = new Customer(parkingBoy);
        Ticket ticket1 = customer.parkCar(parkingLot1, car1);
        Ticket ticket2 = customer.parkCar(parkingLot2, car2);

        // when
        customer.fetchCar(parkingLot1, ticket1);
        ParkingLotException exception1 = assertThrows(ParkingLotException.class, () -> customer.fetchCar(parkingLot2, ticket1));
        ParkingLotException exception2 = assertThrows(ParkingLotException.class, () -> customer.fetchCar(parkingLot1, ticket1));

        // then
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception1.getMessage());
        assertEquals(UNRECOGNIZED_PARKING_TICKET, exception2.getMessage());
    }

    @Test
    void should_print_message_when_parkCar_given_2_parkingLots_is_full_and_car_and_parkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car = new Car();
        List<ParkingLot> parkingLots = List.of(parkingLot1, parkingLot2);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Customer customer = new Customer(parkingBoy);

        Set<Car> cars = new HashSet<>();

        parkingLots.forEach(parkingLot -> {
            for (int i = 0; i < 10; i++) {
                cars.add(new Car(UUID.randomUUID().toString()));
            }
            parkingLot.setCars(cars);
        });

        // when
        ParkingLotException exception = assertThrows(ParkingLotException.class, () -> customer.parkCar(parkingLot1, car));

        // then
        assertEquals(NOT_AVAILABLE_POSITION, exception.getMessage());
    }

    @Test
    void should_park_in_first_parking_lot_when_park_given_2_parkingLots_and_car_and_smartParkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car = new Car();
        List<ParkingLot> parkingLots = List.of(parkingLot1, parkingLot2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Customer customer = new Customer(smartParkingBoy);

        parkingLot1.setCars(IntStream.range(0, 5)
                .mapToObj(i -> new Car(UUID.randomUUID().toString()))
                .collect(Collectors.toSet()));
        parkingLot2.setCars(IntStream.range(0, 8)
                .mapToObj(i -> new Car(UUID.randomUUID().toString()))
                .collect(Collectors.toSet()));

        // when
        Ticket ticket = customer.parkCar(parkingLot2, car);

        // then
        assertNotNull(ticket);
        assertEquals(6, parkingLot1.getCars().size());
        assertEquals(8, parkingLot2.getCars().size());
    }

    @Test
    void should_park_in_second_parking_lot_when_park_given_2_parking_lots_and_second_parking_lot_has_more_position_and_car_and_smartParkingBoy() {
        // given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        Car car = new Car();
        List<ParkingLot> parkingLots = List.of(parkingLot1, parkingLot2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Customer customer = new Customer(smartParkingBoy);

        parkingLot1.setCars(IntStream.range(0, 6)
                .mapToObj(i -> new Car(UUID.randomUUID().toString()))
                .collect(Collectors.toSet()));
        parkingLot2.setCars(IntStream.range(0, 4)
                .mapToObj(i -> new Car(UUID.randomUUID().toString()))
                .collect(Collectors.toSet()));

        // when
        Ticket ticket = customer.parkCar(parkingLot2, car);

        // then
        assertNotNull(ticket);
        assertEquals(6, parkingLot1.getCars().size());
        assertEquals(5, parkingLot2.getCars().size());
    }

}
