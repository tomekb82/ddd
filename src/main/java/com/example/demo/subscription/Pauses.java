package com.example.demo.subscription;

import com.google.common.collect.Lists;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public record Pauses(List<Pause> pauses) {
    public static Pauses defaultPauses() {
        return new Pauses(Lists.newArrayList());
    }

    public boolean canPauseAt(Instant when) {
        return pauses.size() <= 2 && (pauses.size() == 0 || when.isAfter(pauses.get(pauses.size()-1).created().plus(10, DAYS)));
    }

    public Pauses withNewPauseAt(Instant when) {
        List<Pause> newPauses = new ArrayList<>(pauses);
        newPauses.add(new Pause(when));
        return new Pauses(newPauses);
    }
}
