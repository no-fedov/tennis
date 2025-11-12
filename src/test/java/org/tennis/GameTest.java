package org.tennis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tennis.model.Game;
import org.tennis.model.Participant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.WinStreakSeries.TREE_STREAK_COUNT;
import static org.tennis.WinStreakSeries.TWO_STREAK_COUNT;
import static org.tennis.model.Game.ScorePoint.ADVANTAGE;
import static org.tennis.model.Game.ScorePoint.FORTY;
import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.Participant.SECOND;

public class GameTest {

    @Test
    @DisplayName("Завершения гейма при счете \"A-40\" при засчитанном поинте")
    public void successCompleteGame() {
        Game game = new Game();

        Game.Point expectedPoint = new Game.Point(ADVANTAGE, FORTY);

        winPoints(FIRST, game, TREE_STREAK_COUNT);
        winPoints(SECOND, game, TREE_STREAK_COUNT);
        winPoints(FIRST, game, TWO_STREAK_COUNT);

        Game.Point resultPoint = game.getPoints().getLast();

        assertEquals(expectedPoint, resultPoint);
        assertEquals(FIRST, game.getWinner());
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Игра не звершилась при счете \"40-40\" и засчитанном поинте")
    public void gameIsNotComplete() {
        Game game = new Game();

        winPoints(FIRST, game, TREE_STREAK_COUNT);
        winPoints(SECOND, game, TREE_STREAK_COUNT);
        game.point(FIRST);

        assertNull(game.getWinner());
        assertFalse(game.isComplete());
    }

    private void winPoints(Participant participant, Game game, WinStreakSeries winStreakSeries) {
        for (int i = 1; i <= winStreakSeries.getSize(); i++) {
            game.point(participant);
        }
    }
}
