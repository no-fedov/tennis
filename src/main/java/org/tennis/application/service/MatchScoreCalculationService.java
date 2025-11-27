package org.tennis.application.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.model.OngoingMatch;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.MatchScoreCalculator;
import org.tennis.domain.game.Set;
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

        TieBreak tieBreak = currentMatch.getLastSet().flatMap(Set::getTieBreak).orElse(null);
        TiebreakScore tiebreakScore = Objects.isNull(tieBreak)
                ? null
                : scoreCalculator.calculateTiebreakPoint(tieBreak);
        return new MatchScoreDto(
                match.firstPlayerName(),
                match.secondPlayerName(),
                pointScore,
                gameScore,
                setScore,
                tiebreakScore,
                currentMatch.isComplete());
    }
}
