package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.model.OngoingMatch;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.domain.game.Participant;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchPlayService implements MatchPlayUseCase {

    private final OngoingMatchesService ongoingMatchesService;

    @Override
    public OngoingMatch play(UUID uuid, Participant pointWinner) {
        OngoingMatch match = ongoingMatchesService.get(uuid);
        match.play(pointWinner);
        if (match.isComplete()) {
            ongoingMatchesService.delete(uuid);
        }
        return match;
    }
}
