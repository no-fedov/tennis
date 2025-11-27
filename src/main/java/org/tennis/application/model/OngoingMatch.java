package org.tennis.application.model;

import org.tennis.domain.Match;
import org.tennis.domain.Participant;

public record OngoingMatch(String firstPlayerName, String secondPlayerName, Match match) {

    public void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.getWinner() != null;
    }
}
