package com.example.demo.reservation;

import java.util.List;

record Event(EventId eventId, List<Reservation> reservations){}
