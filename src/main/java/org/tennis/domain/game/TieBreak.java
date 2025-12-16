package org.tennis.domain.game;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

import static org.tennis.domain.game.Participant.FIRST;
import static org.tennis.domain.util.MatchProgressChecker.isFinish;

@Getter
public class TieBreak {

    private static final int START_SCORE = 0;
    private static final int SHIFT_POINT = 1;
    private static final int POINTS_GAP_FOR_WIN = 2;
    private static final int SCORE_FOR_WIN = 7;

    private boolean isComplete;
    private Participant winner;
    @Getter(AccessLevel.NONE)
    private final List<TieBreakPoint> points = new LinkedList<>();

    public TieBreak() {
        points.add(new TieBreakPoint(START_SCORE, START_SCORE));
    }

    public void point(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        TieBreakPoint lastPoint = points.getLast();
        TieBreakPoint currentPoint = lastPoint.next(pointWinner);
        points.add(currentPoint);
        if (isLastPoint(currentPoint)) {
            isComplete = true;
            this.winner = pointWinner;
        }
    }

    public TieBreakPoint getLastPoint() {
        return points.getLast();
    }

    private boolean isLastPoint(TieBreakPoint point) {
        return isFinish(point.firstScore, point.secondScore, SCORE_FOR_WIN, POINTS_GAP_FOR_WIN);
    }

    public record TieBreakPoint(int firstScore, int secondScore) {
        TieBreakPoint next(Participant winner) {
            return winner == FIRST
                    ? new TieBreakPoint(firstScore + SHIFT_POINT, secondScore)
                    : new TieBreakPoint(firstScore, secondScore + SHIFT_POINT);
        }
    }
}
