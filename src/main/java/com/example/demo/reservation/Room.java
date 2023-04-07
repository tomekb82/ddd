package com.example.demo.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
public class Room {
    @EqualsAndHashCode.Exclude
    RoomId roomId;

    int number;

    List<Reservation> reservations;

}
