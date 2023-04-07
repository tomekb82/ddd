package com.example.demo.reservation;

import com.example.demo.reservation.acl.LendingACL;
import com.example.demo.reservation.acl.reconsiliation.Reconciliation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ReservationsRefactorController {

    private final RoomReservationService roomReservationService
            = new RoomReservationService(
                    new RoomDatabaseRepository(),
                    new ReservationDatabaseRepository(),
                    new EventDatabaseRepository());

    private final RoomReservationRefactorService roomReservationRefactorService
            = new RoomReservationRefactorService(
            new RoomDatabaseRepository(),
            new ReservationDatabaseRepository(),
            new EventDatabaseRepository());
    Reconciliation reconciliation = new Reconciliation();
    ReservationFacade reservationFacade = new ReservationFacade(roomReservationRefactorService);
    LendingACL lendingACL = new LendingACL(reconciliation, reservationFacade);

    @PostMapping("api/ref/rooms")
    ResponseEntity addRooms() {
        Room room = new Room(new RoomId(UUID.randomUUID()), 21, List.of());
        Room saved = roomReservationService.addRoom(room);
        Room result = lendingACL.addRoom(room, saved);
        return ResponseEntity
                .created(URI.create(String.format("/api/rooms/{}", result.roomId())))
                .build();
    }
}
