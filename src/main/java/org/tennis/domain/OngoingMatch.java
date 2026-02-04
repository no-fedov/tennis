package org.tennis.domain;

import lombok.Getter;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.Participant;

import java.util.Objects;

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

    public void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.isComplete();
    }

    public Player winner() {
        Participant winner = match.getWinner();
        if (Objects.isNull(winner)) {
            throw new MatchNotCompleteException();
        }
        return switch (winner) {
            case FIRST -> first;
            case SECOND -> second;
        };
    }
}
