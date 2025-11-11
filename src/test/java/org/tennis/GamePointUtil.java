package org.tennis;

import org.tennis.model.Game;

public class GamePointUtil {

    public static void pointsByPlayer(int playerNumber, Game game, int numberOfPoints) {
        for (int i = 1; i <= numberOfPoints; i ++) {
            game.point(playerNumber);
        }
    }
}
