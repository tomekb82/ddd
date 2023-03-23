package com.example.demo.reservations.api;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Room add(Room room);

    void update(Room room);

    void delete(RoomId roomId);

    Room getById(RoomId roomId);

    List<Room> getAll();
}
