package com.example.demo.reservation


import spock.lang.Specification

class BlackBoxScenarios extends Specification {

    ReservationsRefactorController controller = new ReservationsRefactorController();


    def 'add room'() {
        var reservation = new Reservation()
        userWantsToAddRoom(reservation)
    }


    void userWantsToAddRoom(Room room) {
        controller.addRoom(room)
    }
}
