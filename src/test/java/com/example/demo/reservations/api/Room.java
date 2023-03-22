package com.example.demo.reservations.api;

import java.util.List;

record Room(int number, List<Reservation> reservations) { }
