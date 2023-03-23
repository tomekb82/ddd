package com.example.demo.reservations.api;

import java.util.List;

record Room(RoomId roomId, int number, List<Reservation> reservations) {
}
