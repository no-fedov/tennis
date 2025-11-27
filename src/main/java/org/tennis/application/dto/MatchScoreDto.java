package org.tennis.application.dto;

import org.tennis.domain.score.GameScore;
import org.tennis.domain.score.PointScore;
import org.tennis.domain.score.SetScore;
import org.tennis.domain.score.TiebreakScore;

public record MatchScoreDto(
        String firstPlayerName,
        String secondPlayerName,
        PointScore pointScore,
        GameScore gameScore,
        SetScore setScore,
        TiebreakScore tiebreakScore,
        boolean isComplete) {
}
