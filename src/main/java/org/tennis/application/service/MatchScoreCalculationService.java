package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.port.in.service.MatchScoreUseCase;
import org.tennis.application.storage.OngoingMatchesInMemoryStorage;
import org.tennis.domain.OngoingMatch;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.MatchScoreCalculator;
import org.tennis.domain.game.Participant;
import org.tennis.domain.game.TieBreak;
import org.tennis.domain.score.GameScore;
import org.tennis.domain.score.PointScore;
import org.tennis.domain.score.SetScore;
import org.tennis.domain.score.TiebreakScore;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchScoreCalculationService implements MatchScoreUseCase {

    private final MatchScoreCalculator scoreCalculator;
    private final OngoingMatchesInMemoryStorage ongoingMatchesInMemoryStorage;

    @Override
    public MatchScoreDto calculate(UUID uuid) {
        OngoingMatch match = ongoingMatchesInMemoryStorage.get(uuid);
        return calculate(match);
    }

    @Override
    public MatchScoreDto calculate(OngoingMatch ongoingMatch) {
        Match currentMatch = ongoingMatch.getMatch();
        PointScore pointScore = scoreCalculator.calculatePoint(currentMatch);
        GameScore gameScore = scoreCalculator.calculateGameScore(currentMatch);
        SetScore setScore = scoreCalculator.calculateSetScore(currentMatch);

        TieBreak tieBreak = currentMatch.getLastSet().getTieBreak().orElse(null);
        TiebreakScore tiebreakScore = Objects.isNull(tieBreak)
                ? null
                : scoreCalculator.calculateTiebreakPoint(tieBreak);
        String winnerName = getWinnerName(ongoingMatch);
        return new MatchScoreDto(
                ongoingMatch.getFirst().name(),
                ongoingMatch.getSecond().name(),
                pointScore,
                gameScore,
                setScore,
                tiebreakScore,
                winnerName,
                ongoingMatch.isComplete()
        );
    }

    private String getWinnerName(OngoingMatch match) {
        Match currentMatch = match.getMatch();
        Participant winner = currentMatch.getWinner();
        if (Objects.isNull(winner)) {
            return null;
        }
        return switch (winner) {
            case FIRST -> match.getFirst().name();
            case SECOND -> match.getSecond().name();
        };
    }
}
