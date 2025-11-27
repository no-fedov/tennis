package org.tennis.domain.game;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.tennis.domain.game.Participant.FIRST;
import static org.tennis.domain.game.Participant.SECOND;
import static org.tennis.domain.util.MatchProgressChecker.isFinish;

@Getter
public class Set {

    private static final int MAX_COUNT_GAME = 12;
    private static final int GAME_COUNT_FOR_WIN = 6;
    private static final int GAP_BY_GAME_FOR_WIN = 2;
    private static final int ZERO_WIN_GAME = 0;

    private boolean isComplete;
    private Participant winner;
    @Getter(AccessLevel.NONE)
    private TieBreak tieBreak;
    @Getter(AccessLevel.NONE)
    protected final List<Game> games = new LinkedList<>();

    public void play(Participant pointWinner) {
        if (isComplete) {
            return;
        }
        if (tieBreak != null) {
            pointTieBreak(pointWinner);
            return;
        }
        if (games.isEmpty()) {
            // TODO: инвертировать зависимость
            games.add(new Game());
        }
        Game lastGame = games.getLast();
        lastGame.point(pointWinner);
        if (thisComplete()) {
            this.winner = pointWinner;
            isComplete = true;
            return;
        }
        if (lastGame.isComplete() && games.size() == MAX_COUNT_GAME) {
            // TODO: инвертировать зависимость
            tieBreak = new TieBreak();
            pointTieBreak(pointWinner);
            return;
        }
        if (lastGame.isComplete()) {
            // TODO: инвертировать зависимость
            games.add(new Game());
        }
    }

    public Optional<Game> getLastGame() {
        if (games.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(games.getLast());
    }

    public Optional<TieBreak> getTieBreak() {
        return Optional.ofNullable(this.tieBreak);
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
