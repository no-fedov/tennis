package org.tennis.application.model;

import lombok.Getter;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.Participant;

import java.util.Objects;

@Getter
public class OngoingMatch {

    private final String firstPlayerName;
    private final Long firstPlayerId;
    private final String secondPlayerName;
    private final Long secondPlayerId;
    private final Match match;

    public OngoingMatch(String firstPlayerName,
                        Long firstPlayerId,
                        String secondPlayerName,
                        Long secondPlayerId) {
        this.firstPlayerName = firstPlayerName;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerName = secondPlayerName;
        this.secondPlayerId = secondPlayerId;
        this.match = new Match();
    }

    public synchronized void play(Participant pointWinner) {
        this.match.play(pointWinner);
    }

    public boolean isComplete() {
        return match.isComplete();
    }

    public Long getWinnerId() {
        return switch (match.getWinner()) {
            case FIRST -> firstPlayerId;
            case SECOND -> secondPlayerId;
        };
    }

    private void checkUniquePlayer(Long firstPlayerId, Long secondPlayerId) {
        if (Objects.equals(firstPlayerId, secondPlayerId)) {
            throw new IllegalStateException();
        }
    }
}
