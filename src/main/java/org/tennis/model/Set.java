package org.tennis.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.Participant.SECOND;
import static org.tennis.model.util.MatchProgressChecker.isFinish;

@Getter
public class Set {

    private static final int MAX_COUNT_GAME = 12;
    private static final int GAME_COUNT_FOR_WIN = 6;
    private static final int GAP_BY_GAME_FOR_WIN = 2;
    private static final int ZERO_WIN_GAME = 0;

    private boolean isComplete;
    private Participant winner;
    private TieBreak tieBreak;
    private final List<Game> games = new LinkedList<>();

    public void play(Participant winner) {
        if (isComplete) {
            return;
        }
        if (tieBreak != null) {
            pointTieBreak(winner);
            return;
        }
        if (games.isEmpty()) {
            // TODO: инвертировать зависимость
            games.add(new Game());
        }
        Game lastGame = games.getLast();
        lastGame.point(winner);
        if (thisComplete()) {
            this.winner = winner;
            isComplete = true;
            return;
        }
        if (lastGame.isComplete() && games.size() == MAX_COUNT_GAME) {
            // TODO: инвертировать зависимость
            tieBreak = new TieBreak();
            pointTieBreak(winner);
            return;
        }
        if (lastGame.isComplete()) {
            // TODO: инвертировать зависимость
            games.add(new Game());
        }
    }

    private boolean thisComplete() {
        Map<Participant, Long> scores = games.stream()
                .filter(game -> game.getWinner() != null)
                .collect(groupingBy(Game::getWinner, counting()));
        int firstScore = scores.getOrDefault(FIRST, (long) ZERO_WIN_GAME).intValue();
        int secondScore = scores.getOrDefault(SECOND, (long) ZERO_WIN_GAME).intValue();
        return isFinish(firstScore, secondScore, GAME_COUNT_FOR_WIN, GAP_BY_GAME_FOR_WIN);
    }

    private void pointTieBreak(Participant winner) {
        tieBreak.point(winner);
        if (tieBreak.isComplete()) {
            this.winner = winner;
            isComplete = true;
        }
    }
}
