package org.tennis.application.port.in.service;

import org.tennis.application.dto.MatchScoreDto;
import org.tennis.domain.OngoingMatch;

import java.util.UUID;

public interface MatchScoreUseCase {
    MatchScoreDto calculate(UUID uuid);

    MatchScoreDto calculate(OngoingMatch match);
}
