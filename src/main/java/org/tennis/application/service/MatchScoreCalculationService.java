package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.model.OngoingMatch;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.MatchScoreCalculator;
import org.tennis.domain.game.TieBreak;
import org.tennis.domain.score.GameScore;
import org.tennis.domain.score.PointScore;
import org.tennis.domain.score.SetScore;
import org.tennis.domain.score.TiebreakScore;

import java.util.Objects;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    private final MatchScoreCalculator scoreCalculator;

    public MatchScoreDto calculatePointGameScore(OngoingMatch match) {
        Match currentMatch = match.match();
        PointScore pointScore = scoreCalculator.calculatePoint(currentMatch);
        GameScore gameScore = scoreCalculator.calculateGameScore(currentMatch);
        SetScore setScore = scoreCalculator.calculateSetScore(currentMatch);

        TieBreak tieBreak = currentMatch.getLastSet().getTieBreak().orElse(null);
        TiebreakScore tiebreakScore = Objects.isNull(tieBreak)
                ? null
                : scoreCalculator.calculateTiebreakPoint(tieBreak);
        String winnerName = getWinnerName(match);
        return new MatchScoreDto(
                match.firstPlayerName(),
                match.secondPlayerName(),
                pointScore,
                gameScore,
                setScore,
                tiebreakScore,
                currentMatch.isComplete(),
                winnerName);
    }

    private String getWinnerName(OngoingMatch match) {
        Match currentMatch = match.match();
        if (currentMatch.getWinner() == null) {
            return null;
        }
        return switch (match.match().getWinner()) {
            case FIRST -> match.firstPlayerName();
            case SECOND -> match.secondPlayerName();
        };
    }
}
