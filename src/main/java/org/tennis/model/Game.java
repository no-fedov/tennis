package org.tennis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.Participant.SECOND;

@Getter
public class Game {

    private Participant winner;
    private boolean isComplete;
    private final List<Point> points = new LinkedList<>();

    public void point(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        if (points.isEmpty()) {
            switch (pointWinner) {
                case FIRST -> points.add(new Point(ScorePoint.FIFTEEN, ScorePoint.ZERO));
                case SECOND -> points.add(new Point(ScorePoint.ZERO, ScorePoint.FIFTEEN));
            }
            return;
        }
        Point previousPoint = points.getLast();
        Point nextPoint = previousPoint.nextPoint(pointWinner);
        if (Objects.equals(previousPoint, nextPoint)) {
            isComplete = true;
            this.winner = pointWinner;
        } else {
            points.add(nextPoint);
        }
    }

    public List<Point> getPoints() {
        return new LinkedList<>(points);
    }

    @RequiredArgsConstructor
    public enum ScorePoint {
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

    public record Point(ScorePoint firstScore, ScorePoint secondScore) {

        private Point nextPoint(Participant pointWinner) {
            if ((pointWinner == FIRST && isLastPoint(firstScore, secondScore))
                    || (pointWinner == SECOND && isLastPoint(secondScore, firstScore))) {
                return new Point(firstScore, secondScore);
            }
            if (pointWinner == FIRST) {
                return secondScore == ScorePoint.ADVANTAGE
                        ? new Point(firstScore, secondScore.next())
                        : new Point(firstScore.next(), secondScore);
            } else {
                return firstScore == ScorePoint.ADVANTAGE
                        ? new Point(firstScore.next(), secondScore)
                        : new Point(firstScore, secondScore.next());
            }
        }

        private boolean isLastPoint(ScorePoint firstScore, ScorePoint secondScore) {
            return switch (firstScore) {
                case FORTY -> secondScore != ScorePoint.FORTY && secondScore != ScorePoint.ADVANTAGE;
                case ADVANTAGE -> secondScore == ScorePoint.FORTY;
                default -> false;
            };
        }
    }
}
