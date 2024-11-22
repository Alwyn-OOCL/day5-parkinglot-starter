package com.parkinglot;

import java.util.Objects;
import java.util.UUID;

public class Ticket {

    private String id;

    public Ticket(String id) {
        this.id = id;
    }

    public Ticket() {
    }

    public static Ticket generateTicket() {
        return new Ticket(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
