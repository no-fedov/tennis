package org.tennis.application.port.out.persistence;

public record MatchCompletedCreate(Long firstPlayerId, Long secondPlayerId, Long winnerId) {

    public MatchCompletedCreate {
        checkWinnerOneOfParticipants(firstPlayerId, secondPlayerId, winnerId);
    }

    void checkWinnerOneOfParticipants(Long firstPlayerId, Long secondPlayerId, Long winnerId) {
        if (!winnerId.equals(firstPlayerId) && !winnerId.equals(secondPlayerId)) {
            throw new IllegalStateException();
        }
    }
}
