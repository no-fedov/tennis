package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.port.in.service.CreateOngoingMatchUseCase;
import org.tennis.application.port.in.service.PlayerCreate;
import org.tennis.application.port.out.persistence.PlayerService;
import org.tennis.application.storage.OngoingMatchesInMemoryStorage;
import org.tennis.domain.OngoingMatch;
import org.tennis.domain.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateOngoingMatchService implements CreateOngoingMatchUseCase {

    private final OngoingMatchesInMemoryStorage ongoingMatchesInMemoryStorage;
    private final PlayerService playerService;

    @Override
    public UUID create(String firstPlayerName, String secondPlayerName) {
        Long firstPlayerId = playerService.create(new PlayerCreate(firstPlayerName));
        Long secondPlayerId = playerService.create(new PlayerCreate(secondPlayerName));
        Player first = new Player(firstPlayerId, firstPlayerName);
        Player second = new Player(secondPlayerId, secondPlayerName);
        OngoingMatch ongoingMatch = new OngoingMatch(first, second);
        return ongoingMatchesInMemoryStorage.add(ongoingMatch);
    }
}
