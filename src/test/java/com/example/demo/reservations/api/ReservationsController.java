package com.example.demo.reservations.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController("/api/")
public class ReservationsController {

    List<Room> rooms = new ArrayList<>();

    @PostMapping("/rooms")
    ResponseEntity addRoom(@RequestBody Room room) {
        rooms.add(room);
        return ResponseEntity
                .created(URI.create("/api/rooms/123"))
                .build();
    }

    @GetMapping("/rooms")
    ResponseEntity getRooms() {
        if(rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/{roomId}")
    ResponseEntity getRoomById(@PathVariable String roomId) {
        if(rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms.get(0));
    }

    @GetMapping("/{id}")
    ResponseEntity updateRoom(@PathVariable Room room) {
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/rooms/{id}/reservations")
    ResponseEntity addReservation(@PathVariable String roomId, @RequestBody Reservation reservation) {
        rooms.get(0).reservations().add(reservation);
        return ResponseEntity
                .created(URI.create("/api/rooms/123"))
                .build();
    }

    @GetMapping("/rooms/{id}/reservations")
    ResponseEntity getReservations() {
        List<Reservation> reservations = rooms.get(0).reservations();
        if(reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/rooms/{roomId}/reservations/{id}")
    ResponseEntity getReservationbById(@PathVariable String id) {
        if(rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms.get(0));
    }

    @PutMapping("/rooms/{roomId}/reservations/{id}")
    ResponseEntity updateReservation(@PathVariable String id) {
        if(rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms.get(0));
    }

    @PostMapping("/events")
    ResponseEntity addEvent(@RequestBody Event event) {
        return ResponseEntity.ok(event);
    }

    @GetMapping("/events")
    ResponseEntity getAllEvents(@RequestBody Event event) {
        return ResponseEntity.ok(event);
    }

    @GetMapping("/events/{id}")
    ResponseEntity getEventById(@RequestBody Event event) {
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/events/{id}")
    ResponseEntity deleteEvents(@RequestBody Event event) {
        return ResponseEntity.ok(event);
    }
}
