package org.tennis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tennis.model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.GamePointUtil.pointsByPlayer;

public class GameTest {

    @Test
    @DisplayName("Завершения гейма при счете \"A-40\" при засчитанном поинте")
    public void successCompleteGame() {
        Game game = new Game();
        int winner = 1;
        int looser = 2;

        pointsByPlayer(winner, game, 3);
        pointsByPlayer(looser, game, 3);
        pointsByPlayer(winner, game, 2);

        assertEquals(winner, 1);
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Игра не звершилась при счете\"40-40\" и засчитанном поинте")
    public void successGameIsNotComplete() {
        Game game = new Game();
        int firstPlayer = 1;
        int secondPlayer = 2;

        pointsByPlayer(firstPlayer, game, 3);
        pointsByPlayer(secondPlayer, game, 3);
        game.point(2);
        assertNull(game.getWinner());
        assertFalse(game.isComplete());
    }
}
