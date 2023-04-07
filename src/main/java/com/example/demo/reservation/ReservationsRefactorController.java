package com.example.demo.reservation;

import com.example.demo.reservation.acl.LendingACL;
import com.example.demo.reservation.acl.reconsiliation.Reconciliation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ReservationsRefactorController {

    @Autowired
    LendingACL lendingACL;

    private final RoomReservationService roomReservationService
            = new RoomReservationService(
                    new RoomDatabaseRepository(),
                    new ReservationDatabaseRepository(),
                    new EventDatabaseRepository());


    @PostMapping("api/ref/rooms")
    ResponseEntity addRooms() {
        Room room = Room.builder()
                .roomId(new RoomId(UUID.randomUUID()))
                .number( 21)
                .reservations(List.of())
                .build();
        Room saved = roomReservationService.addRoom(room);
        Room result = lendingACL.addRoom(room, saved);
        return ResponseEntity
                .created(URI.create(String.format("/api/rooms/{}", result.getRoomId().id())))
                .build();
    }
}
