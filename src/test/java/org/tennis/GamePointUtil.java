package org.tennis;

import org.tennis.model.Game;
import org.tennis.model.Participant;

public class GamePointUtil {

    public static void pointsByPlayer(Participant participant, Game game, int numberOfPoints) {
        for (int i = 1; i <= numberOfPoints; i++) {
            game.point(participant);
        }
    }
}
