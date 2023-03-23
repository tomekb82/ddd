package com.example.demo.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservationDatabaseRepository implements ReservationRepository{

    Map<ReservationId, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation add(Reservation reservation) {
        reservation.reservationId = new ReservationId(UUID.randomUUID());
        reservations.put(reservation.reservationId, reservation);
        return reservation;
    }

    @Override
    public void cancel(ReservationId reservationId) {
        reservations.remove(reservationId);
    }

    @Override
    public void update(Reservation reservation) {
        reservations.put(reservation.reservationId, reservation);
    }

    @Override
    public List<Reservation> getAllBy(RoomId roomId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.roomId.equals(roomId))
                .toList();
    }

    @Override
    public Reservation getById(ReservationId reservationId) {
        return reservations.get(reservationId);
    }

}
