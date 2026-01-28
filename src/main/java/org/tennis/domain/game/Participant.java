package org.tennis.domain.game;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Participant {
    FIRST("FIRST"),
    SECOND("SECOND");

    private final String description;

    public static Participant getParticipant(String string) {
        return switch (string) {
            case "FIRST" -> FIRST;
            case "SECOND" -> SECOND;
            default -> throw new IllegalStateException();
        };
    }
}
