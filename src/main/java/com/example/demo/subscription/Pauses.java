package com.example.demo.subscription;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record Pauses(List<Pause> pauses) {
    public static Pauses defaultPauses() {
        return new Pauses(List.of(new Pause(Instant.now())));
    }

    public boolean canPauseAt(Instant when) {
        return pauses.size() <=2;
    }

    public Pauses withNewPauseAt(Instant when) {
        List<Pause> newPauses = new ArrayList<>(pauses);
        newPauses.add(new Pause(when));
        return new Pauses(newPauses);
    }
}
