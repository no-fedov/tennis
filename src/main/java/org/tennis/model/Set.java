package org.tennis.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.tennis.model.util.MatchProgressChecker.isFinish;

@Getter
public class Set {

    private static final int TOTAL_COUNT_GAME = 12;
    private static final int GAME_COUNT_FOR_WIN = 6;
    private static final int GAP_BY_GAME_FOR_WIN = 2;

    boolean isComplete;
    Integer winner;
    private TieBreak tieBreak;
    private final List<Game> games = new LinkedList<>();

    public void play(int playerNumber) {
        if (isComplete) {
            return;
        }
        if (tieBreak != null) {
            pointTieBreak(playerNumber);
            return;
        }
        Game lastGame = getLastOrInitGame();
        if (lastGame.isComplete() && games.size() == TOTAL_COUNT_GAME) {
            if (tieBreak == null) {
                tieBreak = new TieBreak();
            }
            pointTieBreak(playerNumber);
            return;
        }
        lastGame.point(playerNumber);
        if (thisComplete()) {
            winner = playerNumber;
            isComplete = true;
        }
    }

    private Game getLastOrInitGame() {
        Game lastGame = games.getLast();
        if (games.isEmpty() || lastGame.isComplete()) {
            games.add(new Game());
        }
        return games.getLast();
    }

    private boolean thisComplete() {
        Map<Integer, Long> scores = games.stream()
                .filter(game -> game.getWinner() != null)
                .collect(groupingBy(Game::getWinner, counting()));
        int firstScore = scores.get(1).intValue();
        int secondScore = scores.get(2).intValue();
        return isFinish(firstScore, secondScore, GAME_COUNT_FOR_WIN, GAP_BY_GAME_FOR_WIN);
    }

    private void pointTieBreak(int playerNumber) {
        if (tieBreak.isComplete()) {
            return;
        }
        tieBreak.point(playerNumber);
    }
}
