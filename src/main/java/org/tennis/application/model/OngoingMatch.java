package org.tennis.application.model;

import org.tennis.domain.game.Match;
import org.tennis.domain.game.Participant;

public record OngoingMatch(String firstPlayerName, String secondPlayerName, Match match) {

    public void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.getWinner() != null;
    }
}
