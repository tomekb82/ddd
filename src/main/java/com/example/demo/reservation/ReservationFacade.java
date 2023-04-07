package com.example.demo.reservation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationFacade {

  private final RoomReservationRefactorService service;

  public Room addRoom(Room room) {
      return service.addRoom(room);
  }

}
