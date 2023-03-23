package com.example.demo.reservation;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RoomReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    Room addRoom(Room room) {
        return roomRepository.add(room);
    }

    void updateRoom(Room room){
        roomRepository.update(room);
    }

    void deleteRoom(RoomId roomId){
        roomRepository.delete(roomId);
    }

    Room getById(RoomId roomId){
        return roomRepository.getById(roomId);
    }

    List<Room> getAll() {
        return roomRepository.getAll();
    }

    String printRooms() {
        return getAll().toString();
    }

    List<Reservation> getAllReservationsFor(RoomId roomId) {
        return reservationRepository.getAllBy(roomId);
    }

    String printReservationsFor(RoomId roomId) {
        return getAllReservationsFor(roomId).toString();
    }

    Reservation addReservation(Reservation reservation){
        return reservationRepository.add(reservation);
    }

    void cancelReservation(ReservationId reservationId) {
        reservationRepository.cancel(reservationId);
    }

    void updateReservation(Reservation reservation){
        reservationRepository.update(reservation);
    }

    Reservation getReservationById(ReservationId reservationId){
        return reservationRepository.getById(reservationId);
    }

    public void cancelEvent(EventId eventId) {
        eventRepository.cancel(eventId);
    }

    public Event getEventById(EventId eventId) {
        return eventRepository.getById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public Event addEvent(Event event) {
        return eventRepository.add(event);
    }
}
