package com.example.demo.reservation.acl;

import com.example.demo.reservation.ReservationFacade;
import com.example.demo.reservation.Room;
import com.example.demo.reservation.acl.reconsiliation.Reconciliation;
import com.example.demo.reservation.acl.toggles.NewModelToggles;

public class LendingACL {

    private final Reconciliation<Room> reconciliation;
    private final ReservationFacade facade;

    public LendingACL(Reconciliation<Room> reconciliation, ReservationFacade facade) {
        this.reconciliation = reconciliation;
        this.facade = facade;
    }

    public Room addRoom(Room room, Room oldModelResult){
        if(NewModelToggles.RECONCILE_AND_USE_NEW_MODEL.isActive()){
            Room newModelResult = facade.addRoom(room);
            reconciliation.compare(oldModelResult, newModelResult);
            return newModelResult;
        }
        if(NewModelToggles.RECONCILE_NEW_MODEL.isActive()){
            Room newModelResult = facade.addRoom(room);
            reconciliation.compare(oldModelResult, newModelResult);
            return oldModelResult;
        }
        return oldModelResult;
    }
}
