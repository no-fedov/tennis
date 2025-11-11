package org.tennis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tennis.model.Game;
import org.tennis.model.Participant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.GamePointUtil.pointsByPlayer;
import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.Participant.SECOND;

public class GameTest {

    @Test
    @DisplayName("Завершения гейма при счете \"A-40\" при засчитанном поинте")
    public void successCompleteGame() {
        Game game = new Game();
        Participant winner = FIRST;
        Participant looser = SECOND;

        pointsByPlayer(winner, game, 3);
        pointsByPlayer(looser, game, 3);
        pointsByPlayer(winner, game, 2);

        assertEquals(winner, FIRST);
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Игра не звершилась при счете\"40-40\" и засчитанном поинте")
    public void successGameIsNotComplete() {
        Game game = new Game();
        Participant firstPlayer = FIRST;
        Participant secondPlayer = SECOND;

        pointsByPlayer(firstPlayer, game, 3);
        pointsByPlayer(secondPlayer, game, 3);
        game.point(secondPlayer);
        assertNull(game.getWinner());
        assertFalse(game.isComplete());
    }
}
