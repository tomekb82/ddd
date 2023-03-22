package com.example.demo.reservations.api;

import java.time.LocalDateTime;

public class Reservation {
    enum Type { Room, Conference}
    enum TableSet {TRIANGLE, SQUARE}
    enum Status {NEW, CANCELED, COMPLETED}

    Type type;
    LocalDateTime date;
    int numOfPeople;
    TableSet tableSet;
    Status status;
}
