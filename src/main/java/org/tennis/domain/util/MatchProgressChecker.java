package org.tennis.domain.util;

public class MatchProgressChecker {

    public static boolean isFinish(int firstScore, int secondScore, int scoreForWin, int gapScoreForWin) {
        return playerFinishProgress(firstScore, secondScore, scoreForWin, gapScoreForWin)
                || playerFinishProgress(secondScore, firstScore, scoreForWin, gapScoreForWin);
    }

    private static boolean playerFinishProgress(int firstScore, int secondScore, int scoreForWin, int gapScoreForWin) {
        return firstScore >= scoreForWin && firstScore - secondScore >= gapScoreForWin;
    }
}
