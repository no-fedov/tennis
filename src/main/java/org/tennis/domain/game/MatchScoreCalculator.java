package org.tennis.domain.game;

import org.tennis.domain.score.GameScore;
import org.tennis.domain.score.PointScore;
import org.tennis.domain.score.SetScore;
import org.tennis.domain.score.TiebreakScore;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class MatchScoreCalculator {

    private static final int START_SCORE = 0;

    public PointScore calculatePoint(Match match) {
        Game.Point lastPoint = match.getLastSet()
                .flatMap(Set::getLastGame)
                .flatMap(Game::getLastPoint)
                .orElse(new Game.Point(Game.PointScore.ZERO, Game.PointScore.ZERO));
        return new PointScore(lastPoint.firstScore().description, lastPoint.secondScore().description);
    }

    public TiebreakScore calculateTiebreakPoint(TieBreak tieBreak) {
        TieBreak.TieBreakPoint lastPoint = tieBreak.getLastPoint()
                .orElse(new TieBreak.TieBreakPoint(START_SCORE, START_SCORE));
        return new TiebreakScore(lastPoint.firstScore(), lastPoint.secondScore());
    }

    public GameScore calculateGameScore(Match match) {
        Optional<Set> lastSet = match.getLastSet();
        if (lastSet.isEmpty()) {
            return new GameScore(START_SCORE, START_SCORE);
        }
        Set currentSet = lastSet.get();
        Map<Participant, Integer> playersGameScore = currentSet.games.stream()
                .filter(Game::isComplete)
                .collect(groupingBy(Game::getWinner, collectingAndThen(counting(), Long::intValue)));
        return new GameScore(
                playersGameScore.getOrDefault(Participant.FIRST, START_SCORE),
                playersGameScore.getOrDefault(Participant.SECOND, START_SCORE)
        );
    }

    public SetScore calculateSetScore(Match match) {
        Map<Participant, Integer> playersSetScore = match.sets.stream()
                .filter(Set::isComplete)
                .collect(groupingBy(Set::getWinner, collectingAndThen(counting(), Long::intValue)));
        return new SetScore(
                playersSetScore.getOrDefault(Participant.FIRST, START_SCORE),
                playersSetScore.getOrDefault(Participant.SECOND, START_SCORE)
        );
    }
}
