package org.tennis.application.port.in.service;

import org.tennis.application.model.OngoingMatch;
import org.tennis.domain.game.Participant;

import java.util.UUID;

public interface MatchPlayUseCase {

   OngoingMatch play(UUID uuid, Participant player);
}
