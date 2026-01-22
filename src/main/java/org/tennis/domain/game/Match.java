package org.tennis.domain.game;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Match {

    private static final int MIN_SETS_COUNT = 2;

    private Participant winner;
    private boolean isComplete;
    @Getter(AccessLevel.NONE)
    protected final List<Set> sets = new LinkedList<>();

    public Match() {
        sets.add(new Set());
    }

    public void play(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        Set lastSet = getLastSet();
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

    public Set getLastSet() {
        return sets.getLast();
    }

    private boolean isLastPoint(Participant pointWinner) {
        return sets.size() >= MIN_SETS_COUNT
                && sets.stream().filter(set -> set.getWinner() == pointWinner).count() > 1;
    }
}

