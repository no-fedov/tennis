package org.tennis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.util.MatchProgressChecker.isFinish;

@Getter
@RequiredArgsConstructor
public class TieBreak {

    private static final int START_SCORE = 0;
    private static final int SHIFT_POINT = 1;
    private static final int POINTS_GAP_FOR_WIN = 2;
    private static final int SCORE_FOR_WIN = 7;

    private boolean isComplete;
    private Participant winner;
    private final List<TieBreakPoint> points = new LinkedList<>();

    public void point(Participant winner) {
        if (isComplete) {
            return;
        }
        if (points.isEmpty()) {
            switch (winner) {
                case FIRST -> points.add(new TieBreakPoint(START_SCORE + SHIFT_POINT, START_SCORE));
                case SECOND -> points.add(new TieBreakPoint(START_SCORE, START_SCORE + SHIFT_POINT));
            }
            return;
        }
        TieBreakPoint lastPoint = points.getLast();
        TieBreakPoint currentPoint = lastPoint.next(winner);
        points.add(currentPoint);
        if (isLastPoint(currentPoint)) {
            isComplete = true;
            this.winner = winner;
        }
    }

    private boolean isLastPoint(TieBreakPoint point) {
        return isFinish(point.firstScore, point.secondScore, SCORE_FOR_WIN, POINTS_GAP_FOR_WIN);
    }

    @RequiredArgsConstructor
    static final class TieBreakPoint {
        private final int firstScore;
        private final int secondScore;

        TieBreakPoint next(Participant winner) {
            return winner == FIRST
                    ? new TieBreakPoint(firstScore + SHIFT_POINT, secondScore)
                    : new TieBreakPoint(firstScore, secondScore + SHIFT_POINT);
        }
    }
}
