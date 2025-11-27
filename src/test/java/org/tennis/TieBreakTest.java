package org.tennis;

import org.junit.jupiter.api.Test;
import org.tennis.domain.game.Participant;
import org.tennis.domain.game.TieBreak;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.WinStreakSeries.SEVEN_STREAK_COUNT;
import static org.tennis.domain.game.Participant.FIRST;

public class TieBreakTest {

    @Test
    public void onePoint() {
        TieBreak tieBreak = new TieBreak();

        tieBreak.point(FIRST);
        TieBreak.TieBreakPoint point = tieBreak.getLastPoint().orElseThrow();
        int firstScore = point.firstScore();
        int secondScore = point.secondScore();

        assertEquals(1, firstScore);
        assertEquals(0, secondScore);
    }

    @Test
    public void completeWithScore7_0() {
        TieBreak tieBreak = new TieBreak();

        winStreakPoints(FIRST, tieBreak, SEVEN_STREAK_COUNT);
        TieBreak.TieBreakPoint point = tieBreak.getLastPoint().orElseThrow();

        assertTrue(tieBreak.isComplete());
        assertEquals(FIRST, tieBreak.getWinner());
        assertEquals(SEVEN_STREAK_COUNT.getCount(), point.firstScore());
    }

    private void winStreakPoints(Participant participant, TieBreak tieBreak, WinStreakSeries winStreakSeries) {
        for (int i = 1; i <= winStreakSeries.getCount(); i++) {
            tieBreak.point(participant);
        }
    }
}
