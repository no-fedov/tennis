package org.tennis;

import org.junit.jupiter.api.Test;
import org.tennis.model.Participant;
import org.tennis.model.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.WinStreakSeries.FIVE_STREAK_COUNT;
import static org.tennis.WinStreakSeries.FOUR_STREAK_COUNT;
import static org.tennis.WinStreakSeries.ONE_STREAK_COUNT;
import static org.tennis.WinStreakSeries.SIX_STREAK_COUNT;
import static org.tennis.model.Participant.FIRST;
import static org.tennis.model.Participant.SECOND;

public class SetTest {

    private static final int MAX_GAME_COUNT = 12;

    @Test
    public void setCompleteWithScore_6_0() {
        Set set = new Set();

        winStreakSeriesGame(FIRST, set, SIX_STREAK_COUNT);

        assertTrue(set.isComplete());
        assertEquals(set.getWinner(), FIRST);
    }

    @Test
    public void setIsNotCompleteWithScore_6_6_andInitTieBreak() {
        Set set = new Set();

        winStreakSeriesGame(FIRST, set, FIVE_STREAK_COUNT);
        winStreakSeriesGame(SECOND, set, FIVE_STREAK_COUNT);
        winStreakSeriesGame(FIRST, set, ONE_STREAK_COUNT);
        winStreakSeriesGame(SECOND, set, ONE_STREAK_COUNT);

        assertEquals(set.getGames().size(), MAX_GAME_COUNT);
        assertFalse(set.isComplete());
        assertNotNull(set.getTieBreak());
    }

    private void winGame(Participant participant, Set set) {
        for (int i = 1; i <= FOUR_STREAK_COUNT.getSize(); i++) {
            set.play(participant);
        }
    }

    private void winStreakSeriesGame(Participant participant, Set set, WinStreakSeries streakSeries) {
        for (int i = 1; i <= streakSeries.getSize(); i++) {
            winGame(participant, set);
        }
    }
}
