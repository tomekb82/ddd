package com.example.demo.reservation.acl.toggles;

import com.example.demo.reservation.*;
import com.example.demo.reservation.acl.LendingACL;
import com.example.demo.reservation.acl.reconsiliation.Reconciliation;
import com.example.demo.reservation.acl.toggles.NewModelToggles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;

@Configuration
public class ToggleConfiguration {

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(NewModelToggles.class);
    }

    @Bean
    public LendingACL lendingACL() {
        RoomReservationRefactorService roomReservationRefactorService
                = new RoomReservationRefactorService(
                new RoomDatabaseRepository(),
                new ReservationDatabaseRepository(),
                new EventDatabaseRepository());
        Reconciliation<Room> reconciliation = new Reconciliation<>();
        ReservationFacade reservationFacade = new ReservationFacade(roomReservationRefactorService);
        return new LendingACL(reconciliation, reservationFacade);
    }
}
