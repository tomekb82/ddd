package com.example.demo.reservations.api;

import java.util.*;

public class RoomDatabaseRepository implements RoomRepository{

    Map<RoomId, Room> rooms = new HashMap<>();

    @Override
    public Room add(Room room) {
        Room toSave = new Room(new RoomId(UUID.randomUUID()), room.number(), room.reservations());
        rooms.put(room.roomId(), toSave);
        return toSave;
    }

    @Override
    public void update(Room room) {
        rooms.put(room.roomId(), room);
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
