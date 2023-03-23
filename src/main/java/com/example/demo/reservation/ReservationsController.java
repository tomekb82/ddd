package com.example.demo.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController("/api/")
public class ReservationsController {

    private final RoomReservationService roomReservationService
            = new RoomReservationService(
                    new RoomDatabaseRepository(),
                    new ReservationDatabaseRepository(),
                    new EventDatabaseRepository());

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

    @PutMapping("/{id}")
    ResponseEntity updateRoom(@PathVariable Room room) {
        roomReservationService.updateRoom(room);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/rooms/{id}")
    ResponseEntity deleteRoom(@RequestBody UUID roomId) {
        roomReservationService.deleteRoom(new RoomId(roomId));
        return ResponseEntity.noContent().build();
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
    ResponseEntity updateReservation(
            @PathVariable UUID roomId,
            @PathVariable UUID reservationId,
            @RequestBody Reservation reservation) {
        roomReservationService.updateReservation(reservation);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/rooms/{roomId}/reservations/{id}")
    ResponseEntity cancelReservation(@PathVariable UUID roomId, @PathVariable UUID reservationId) {
        roomReservationService.cancelReservation(new ReservationId(reservationId));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/events")
    ResponseEntity addEvent(@RequestBody Event event) {
        Event saved = roomReservationService.addEvent(event);
        return ResponseEntity
                .created(URI.create(String.format("/api/events/{}", saved.eventId)))
                .build();
    }

    @PutMapping("/events/{id}")
    ResponseEntity updateEvent(@PathVariable UUID eventId, @RequestBody Event event) {
        Event saved = roomReservationService.updateEvent(event);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/events")
    ResponseEntity getAllEvents() {
        List<Event> events = roomReservationService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    ResponseEntity getEventById(@PathVariable UUID eventId) {
        Event event = roomReservationService.getEventById(new EventId(eventId));
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/events/{id}")
    ResponseEntity deleteEvents(@PathVariable UUID eventId) {
        roomReservationService.cancelEvent(new EventId(eventId));
        return ResponseEntity.noContent().build();
    }
}
