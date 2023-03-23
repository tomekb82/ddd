package com.example.demo.reservations.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController("/api/")
public class ReservationsController {

    private final RoomRepository roomRepository = new RoomDatabaseRepository();
    private final ReservationRepository reservationRepository = new ReservationDatabaseRepository();
    private final RoomReservationService roomReservationService
            = new RoomReservationService(roomRepository, reservationRepository);

    @PostMapping("/rooms")
    ResponseEntity addRoom(@RequestBody Room room) {
        Room saved = roomReservationService.addRoom(room);
        return ResponseEntity
                .created(URI.create(String.format("/api/rooms/{}", saved.roomId())))
                .build();
    }

    @GetMapping("/rooms")
    ResponseEntity getRooms() {
        List<Room> rooms = roomReservationService.getAll();
        if(rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/{roomId}")
    ResponseEntity getRoomById(@PathVariable UUID roomId) {
        Room room = roomReservationService.getById(new RoomId(roomId));
        if(room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{id}")
    ResponseEntity updateRoom(@PathVariable Room room) {
        roomReservationService.updateRoom(room);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/rooms/{id}/reservations")
    ResponseEntity addReservation(@PathVariable UUID roomId, @RequestBody Reservation reservation) {
        Reservation saved = roomReservationService.addReservation(reservation);
        return ResponseEntity
                .created(URI.create(String.format("/api/rooms/{}/reservations/{}", roomId, saved.reservationId)))
                .build();
    }

    @GetMapping("/rooms/{id}/reservations")
    ResponseEntity getReservations(UUID roomId) {
        List<Reservation> reservations = roomReservationService.getAllReservationsFor(new RoomId(roomId));
        if(reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/rooms/{roomId}/reservations/{id}")
    ResponseEntity getReservationById(@PathVariable UUID roomId, @PathVariable UUID reservationId) {
        Reservation reservation = roomReservationService.getReservationById(new ReservationId(reservationId));
        if(reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/rooms/{roomId}/reservations/{id}")
    ResponseEntity updateReservation(@RequestBody Reservation reservation) {
        roomReservationService.updateReservation(reservation);
        return ResponseEntity.ok().build();
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
