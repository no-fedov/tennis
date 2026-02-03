package org.tennis.domain.game;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Participant {
    FIRST("FIRST"),
    SECOND("SECOND");

    public static final String INVALID_PARTICIPANT_ATTRIBUTE_MESSAGE = "Invalid participant attribute: %s";

    private final String description;

    public static Participant getParticipant(String string) {
        return switch (string) {
            case "FIRST" -> FIRST;
            case "SECOND" -> SECOND;
            default -> throw new IllegalStateException(INVALID_PARTICIPANT_ATTRIBUTE_MESSAGE.formatted(string));
        };
    }
}
