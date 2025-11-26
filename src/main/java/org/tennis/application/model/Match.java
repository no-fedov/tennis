package org.tennis.application.model;

import org.tennis.domain.Participant;

public record Match(String firstPlayerName, String secondPlayerName, org.tennis.domain.Match match) {

    public void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.getWinner() != null;
    }
}
