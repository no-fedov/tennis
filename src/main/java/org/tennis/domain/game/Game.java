package org.tennis.domain.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.tennis.domain.game.Participant.FIRST;
import static org.tennis.domain.game.Participant.SECOND;

@Getter
public class Game {

    private Participant winner;
    private boolean isComplete;
    @Getter(AccessLevel.NONE)
    private final List<Point> points = new LinkedList<>();

    public void point(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        if (points.isEmpty()) {
            switch (pointWinner) {
                case FIRST -> points.add(new Point(PointScore.FIFTEEN, PointScore.ZERO));
                case SECOND -> points.add(new Point(PointScore.ZERO, PointScore.FIFTEEN));
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

    public Optional<Point> getLastPoint() {
        if (points.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(points.getLast());
    }

    @RequiredArgsConstructor
    @Getter
    public enum PointScore {
        ZERO("0"),
        FIFTEEN("15"),
        THIRTY("30"),
        FORTY("40"),
        ADVANTAGE("A");

        final String description;

        private PointScore next() {
            return switch (this) {
                case ZERO -> FIFTEEN;
                case FIFTEEN -> THIRTY;
                case THIRTY, ADVANTAGE -> FORTY;
                case FORTY -> ADVANTAGE;
            };
        }
    }

    public record Point(PointScore firstScore, PointScore secondScore) {

        private Point nextPoint(Participant pointWinner) {
            if ((pointWinner == FIRST && isLastPoint(firstScore, secondScore))
                    || (pointWinner == SECOND && isLastPoint(secondScore, firstScore))) {
                return new Point(firstScore, secondScore);
            }
            if (pointWinner == FIRST) {
                return secondScore == PointScore.ADVANTAGE
                        ? new Point(firstScore, secondScore.next())
                        : new Point(firstScore.next(), secondScore);
            } else {
                return firstScore == PointScore.ADVANTAGE
                        ? new Point(firstScore.next(), secondScore)
                        : new Point(firstScore, secondScore.next());
            }
        }

        private boolean isLastPoint(PointScore firstScore, PointScore secondScore) {
            return switch (firstScore) {
                case FORTY -> secondScore != PointScore.FORTY && secondScore != PointScore.ADVANTAGE;
                case ADVANTAGE -> secondScore == PointScore.FORTY;
                default -> false;
            };
        }
    }
}
