package org.tennis.domain.util;

public class MatchProgressChecker {

    public static boolean isFinished(int firstScore, int secondScore, int scoreForWin, int gapScoreForWin) {
        int max = Math.max(firstScore, secondScore);
        int min = Math.min(firstScore, secondScore);
        return max >= scoreForWin && (max - min) >= gapScoreForWin;
    }
}
