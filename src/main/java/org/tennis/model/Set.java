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

    private static final int TOTAL_COUNT_GAME = 12;
    private static final int GAME_COUNT_FOR_WIN = 6;
    private static final int GAP_BY_GAME_FOR_WIN = 2;

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
        Game lastGame = getLastOrInitGame();
        if (lastGame.isComplete() && games.size() == TOTAL_COUNT_GAME) {
            if (tieBreak == null) {
                tieBreak = new TieBreak();
            }
            pointTieBreak(winner);
            return;
        }
        lastGame.point(winner);
        if (thisComplete()) {
            this.winner = winner;
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
        Map<Participant, Long> scores = games.stream()
                .filter(game -> game.getWinner() != null)
                .collect(groupingBy(Game::getWinner, counting()));
        int firstScore = scores.get(FIRST).intValue();
        int secondScore = scores.get(SECOND).intValue();
        return isFinish(firstScore, secondScore, GAME_COUNT_FOR_WIN, GAP_BY_GAME_FOR_WIN);
    }

    private void pointTieBreak(Participant winner) {
        if (tieBreak.isComplete()) {
            return;
        }
        tieBreak.point(winner);
    }
}
