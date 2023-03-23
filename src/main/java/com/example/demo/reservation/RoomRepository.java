package com.example.demo.reservation;

import java.util.List;

public interface RoomRepository {

    Room add(Room room);

    void update(Room room);

    void delete(RoomId roomId);

    Room getById(RoomId roomId);

    List<Room> getAll();
}
