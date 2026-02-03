package org.tennis.application.port.in.service;

import org.tennis.application.dto.MatchScoreDto;
import org.tennis.domain.game.Participant;

import java.util.UUID;

public interface MatchPlayUseCase {

   MatchScoreDto play(UUID uuid, Participant player);
}
