package com.example.demo.reservation;

import java.util.*;

public class RoomDatabaseRepository implements RoomRepository{

    Map<RoomId, Room> rooms = new HashMap<>();

    @Override
    public Room add(Room room) {
        Room toSave = room.toBuilder()
                .roomId(new RoomId(UUID.randomUUID()))
                .build();
        rooms.put(room.getRoomId(), toSave);
        return toSave;
    }

    @Override
    public void update(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    @Override
    public void delete(RoomId roomId) {
        rooms.remove(roomId);
    }

    @Override
    public Room getById(RoomId roomId) {
        return rooms.get(roomId);
    }

    @Override
    public List<Room> getAll() {
        return rooms.values()
                .stream()
                .toList();
    }
}
