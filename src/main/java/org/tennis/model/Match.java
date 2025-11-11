package org.tennis.model;


import java.util.LinkedList;
import java.util.List;

public class Match {

    Integer winner;
    boolean isComplete;

    private final List<Set> sets = new LinkedList<>();

    public void playMatch(int playerNumber) {
        if (isComplete) {
            return;
        }
        if (sets.isEmpty()) {
            sets.add(new Set());
        }
        Set lastSet = getLastOrInitSet();
        // TODO: сделать логику игры
    }

    private Set getLastOrInitSet() {
        Set lastSet = sets.getLast();
        if (sets.isEmpty() || lastSet.isComplete()) {
            sets.add(new Set());
        }
        return sets.getLast();
    }

}
