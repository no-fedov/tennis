package org.tennis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Game {

    private Integer winner;
    private boolean isComplete;
    private final List<Point> points = new LinkedList<>();

    public void point(int playerNumber) {
        if (isComplete) {
            return;
        }
        if (points.isEmpty()) {
            switch (playerNumber) {
                case 1 -> points.add(new Point(ScorePoint.FIFTEEN, ScorePoint.ZERO));
                case 2 -> points.add(new Point(ScorePoint.ZERO, ScorePoint.FIFTEEN));
            }
            return;
        }
        Point previousPoint = points.getLast();
        points.add(previousPoint.nextPoint(playerNumber));
    }

    @RequiredArgsConstructor
    enum ScorePoint {
        ZERO("0"),
        FIFTEEN("15"),
        THIRTY("30"),
        FORTY("40"),
        ADVANTAGE("A");

        final String description;

        private ScorePoint next() {
            return switch (this) {
                case ZERO -> FIFTEEN;
                case FIFTEEN -> THIRTY;
                case THIRTY, ADVANTAGE -> FORTY;
                case FORTY -> ADVANTAGE;
            };
        }
    }

    @RequiredArgsConstructor
    class Point {

        private final ScorePoint firstPlayerScore;
        private final ScorePoint secondPlayerScore;

        private Point nextPoint(int playerNumberPointWinner) {
            if ((playerNumberPointWinner == 1 && isLastPoint(firstPlayerScore, secondPlayerScore))
                    || (playerNumberPointWinner == 2 && isLastPoint(secondPlayerScore, firstPlayerScore))) {
                isComplete = true;
                winner = playerNumberPointWinner;
                return this;
            }
            if (playerNumberPointWinner == 1) {
                return secondPlayerScore == ScorePoint.ADVANTAGE
                        ? new Point(firstPlayerScore, secondPlayerScore.next())
                        : new Point(firstPlayerScore.next(), secondPlayerScore);
            } else {
                return firstPlayerScore == ScorePoint.ADVANTAGE
                        ? new Point(firstPlayerScore.next(), secondPlayerScore)
                        : new Point(firstPlayerScore, secondPlayerScore.next());
            }
        }

        private boolean isLastPoint(ScorePoint previousPointWinner, ScorePoint opponentPoint) {
            return switch (previousPointWinner) {
                case FORTY -> opponentPoint != ScorePoint.FORTY && opponentPoint != ScorePoint.ADVANTAGE;
                case ADVANTAGE -> opponentPoint == ScorePoint.FORTY;
                default -> false;
            };
        }
    }
}
