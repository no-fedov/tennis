package org.tennis;

import org.junit.jupiter.api.Test;
import org.tennis.domain.game.Match;
import org.tennis.domain.game.Participant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.WinStreakSeries.FOUR_STREAK_COUNT;
import static org.tennis.WinStreakSeries.SIX_STREAK_COUNT;
import static org.tennis.domain.game.Participant.FIRST;
import static org.tennis.domain.game.Participant.SECOND;

public class MatchTest {

    @Test
    public void matchTest() {
        Match match = new Match();

        winSet(FIRST, match);
        winSet(FIRST, match);

        assertTrue(match.isComplete());
    }

    @Test
    public void matchTest2() {
        Match match = new Match();

        winSet(SECOND, match);
        winSet(SECOND, match);

        assertTrue(match.isComplete());
    }

    private void winSet(Participant winner, Match match) {
        for (int j = 1; j <= SIX_STREAK_COUNT.getCount(); j++) {
            for (int i = 1; i <= FOUR_STREAK_COUNT.getCount(); i++) {
                match.play(winner);
            }
        }
    }
}
