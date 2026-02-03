package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.application.port.out.persistence.MatchCompletedCreate;
import org.tennis.application.port.out.persistence.MatchService;
import org.tennis.application.storage.OngoingMatchesInMemoryStorage;
import org.tennis.domain.OngoingMatch;
import org.tennis.domain.game.Participant;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchPlayService implements MatchPlayUseCase {

    private final OngoingMatchesInMemoryStorage ongoingMatchesInMemoryStorage;
    private final MatchScoreCalculationService matchScoreCalculationService;
    private final MatchService matchService;

    @Override
    public MatchScoreDto play(UUID uuid, Participant pointWinner) {
        OngoingMatch match = ongoingMatchesInMemoryStorage.get(uuid);
        match.play(pointWinner);
        if (match.isComplete()) {
            ongoingMatchesInMemoryStorage.delete(uuid);
            MatchCompletedCreate completedMatch = new MatchCompletedCreate(match.getFirst().id(),
                    match.getSecond().id(),
                    match.getWinner().id());
            matchService.create(completedMatch);
        }
        return matchScoreCalculationService.calculate(match);
    }
}
