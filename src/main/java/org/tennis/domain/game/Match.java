package org.tennis.domain.game;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
public class Match {

    private static final int MIN_SETS_COUNT = 2;

    private Participant winner;
    private boolean isComplete;
    @Getter(AccessLevel.NONE)
    protected final List<Set> sets = new LinkedList<>();

    public void play(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        if (sets.isEmpty()) {
            // TODO: инвертировать зависимость
            sets.add(new Set());
        }
        Set lastSet = sets.getLast();
        lastSet.play(pointWinner);
        if (isLastPoint(pointWinner)) {
            this.winner = pointWinner;
            isComplete = true;
            return;
        }
        if (lastSet.isComplete()) {
            sets.add(new Set());
        }
    }

    public Optional<Set> getLastSet() {
        if (sets.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sets.getLast());
    }

    private boolean isLastPoint(Participant pointWinner) {
        return sets.size() >= MIN_SETS_COUNT
                && sets.stream().filter(set -> set.getWinner() == pointWinner).count() > 1;
    }
}

