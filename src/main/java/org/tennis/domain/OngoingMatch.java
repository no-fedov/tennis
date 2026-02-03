package org.tennis.domain;

import lombok.Getter;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.Participant;

@Getter
public class OngoingMatch {

    private final Player first;
    private final Player second;
    private final Match match;

    public OngoingMatch(Player first, Player second) {
        this.first = first;
        this.second = second;
        this.match = new Match();
    }

    public synchronized void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.isComplete();
    }

    public Player getWinner() {
        return switch (match.getWinner()) {
            case FIRST -> first;
            case SECOND -> second;
        };
    }
}
