package com.example.demo.reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation add(Reservation reservation);

    void cancel(ReservationId reservationId);

    void update(Reservation reservation);

    List<Reservation> getAllBy(RoomId roomId);

    Reservation getById(ReservationId reservationId);
}
