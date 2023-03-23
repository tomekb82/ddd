package com.example.demo.reservation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {
    enum Type { Room, Conference}
    enum TableSet {TRIANGLE, SQUARE}
    enum Status {NEW, CANCELED, COMPLETED}

    ReservationId reservationId;
    RoomId roomId;
    Type type;
    LocalDateTime date;
    int numOfPeople;
    TableSet tableSet;
    Status status;
}
