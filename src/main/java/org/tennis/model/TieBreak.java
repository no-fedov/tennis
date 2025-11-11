package org.tennis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static org.tennis.model.util.MatchProgressChecker.isFinish;

@Getter
@RequiredArgsConstructor
public class TieBreak {

    private static final int START_SCORE = 0;
    private static final int SHIFT_POINT = 1;
    private static final int POINTS_GAP_FOR_WIN = 2;
    private static final int SCORE_FOR_WIN = 7;

    private boolean isComplete;
    private Integer winner;
    private final List<TieBreakPoint> tieBreakPoints = new LinkedList<>();

    public void point(int playerNumber) {
        if (isComplete) {
            return;
        }
        if (tieBreakPoints.isEmpty()) {
            tieBreakPoints.add(new TieBreakPoint(START_SCORE, START_SCORE));
        }
        TieBreakPoint lastPoint = tieBreakPoints.getLast();
        TieBreakPoint currentPoint = lastPoint.next(playerNumber);
        tieBreakPoints.add(currentPoint);
        if (isLastPoint(currentPoint)) {
            isComplete = true;
            winner = playerNumber;
        }
    }

    private boolean isLastPoint(TieBreakPoint point) {
        return isFinish(point.firstPlayerScore, point.secondPlayerScore, SCORE_FOR_WIN, POINTS_GAP_FOR_WIN) ;
    }

    @RequiredArgsConstructor
    final class TieBreakPoint {
        private final int firstPlayerScore;
        private final int secondPlayerScore;

        TieBreakPoint next(int playerNumber) {
            return playerNumber == 1
                    ? new TieBreakPoint(firstPlayerScore + SHIFT_POINT, secondPlayerScore)
                    : new TieBreakPoint(firstPlayerScore, secondPlayerScore + SHIFT_POINT);
        }
    }
}
