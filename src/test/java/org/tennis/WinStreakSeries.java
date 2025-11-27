package org.tennis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WinStreakSeries {

    ONE_STREAK_COUNT(1),
    TWO_STREAK_COUNT(2),
    TREE_STREAK_COUNT(3),
    FOUR_STREAK_COUNT(4),
    FIVE_STREAK_COUNT(5),
    SIX_STREAK_COUNT(6),
    SEVEN_STREAK_COUNT(7);

    private final int count;
}

