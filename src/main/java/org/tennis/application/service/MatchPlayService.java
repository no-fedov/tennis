package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.application.port.out.persistence.MatchCompletedCreate;
import org.tennis.application.port.out.persistence.MatchService;
import org.tennis.application.storage.OngoingMatchesInMemoryStorage;
import org.tennis.domain.OngoingMatch;
import org.tennis.domain.game.Participant;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class MatchPlayService implements MatchPlayUseCase {

    private final OngoingMatchesInMemoryStorage ongoingMatchesInMemoryStorage;
    private final MatchScoreCalculationService matchScoreCalculationService;
    private final MatchService matchService;
    private final Map<UUID, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public MatchScoreDto play(UUID uuid, Participant pointWinner) {
        OngoingMatch match = ongoingMatchesInMemoryStorage.get(uuid);
        Lock matchLock = locks.computeIfAbsent(uuid, id -> new ReentrantLock());
        matchLock.lock();
        try {
            match.play(pointWinner);
            if (match.isComplete()) {
                locks.remove(uuid, matchLock);
                ongoingMatchesInMemoryStorage.delete(uuid);
                MatchCompletedCreate completedMatch = new MatchCompletedCreate(match.getFirst().id(),
                        match.getSecond().id(),
                        match.winner().id());
                matchService.create(completedMatch);
            }
            return matchScoreCalculationService.calculate(match);
        } finally {
            matchLock.unlock();
        }
    }
}
