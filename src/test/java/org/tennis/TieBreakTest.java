package org.tennis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tennis.model.Participant;
import org.tennis.model.TieBreak;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tennis.WinStreakSeries.SEVEN_STREAK_COUNT;
import static org.tennis.model.Participant.FIRST;

public class TieBreakTest {

    @Test
    public void onePoint() {
        TieBreak tieBreak = new TieBreak();

        tieBreak.point(FIRST);
        TieBreak.TieBreakPoint point = tieBreak.getPoints().getLast();
        int firstScore = point.firstScore();
        int secondScore = point.secondScore();

        assertEquals(1, firstScore);
        assertEquals(0, secondScore);
    }

    @Test
    public void completeWithScore7_0() {
        TieBreak tieBreak = new TieBreak();

        winStreakPoints(FIRST, tieBreak, SEVEN_STREAK_COUNT);
        List<TieBreak.TieBreakPoint> points = tieBreak.getPoints();

        assertTrue(tieBreak.isComplete());
        assertEquals(FIRST, tieBreak.getWinner());
        assertEquals(points.size(), SEVEN_STREAK_COUNT.getSize());
    }

    private void winStreakPoints(Participant participant, TieBreak tieBreak, WinStreakSeries winStreakSeries) {
        for (int i = 1; i <= winStreakSeries.getSize(); i++) {
            tieBreak.point(participant);
        }
    }
}
