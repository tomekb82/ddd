package com.example.demo.reservation;

import java.util.List;

record Room(RoomId roomId, int number, List<Reservation> reservations) {
}
