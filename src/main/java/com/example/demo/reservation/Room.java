package com.example.demo.reservation;

import java.util.List;

public record Room(RoomId roomId, int number, List<Reservation> reservations) {
}
