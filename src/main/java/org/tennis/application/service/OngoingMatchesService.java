package org.tennis.application.service;

import org.tennis.application.model.OngoingMatch;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private static final String EXCEPTION_MESSAGE_TEMPLATE = "Match with uuid = %s not exists or completed";

    private final Map<UUID, OngoingMatch> matches = new ConcurrentHashMap<>();

    public UUID add(OngoingMatch match) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, match);
        return uuid;
    }

    public OngoingMatch get(UUID uuid) {
        OngoingMatch match = matches.get(uuid);
        if (Objects.isNull(match)) {
            throw new NotFoundException(EXCEPTION_MESSAGE_TEMPLATE.formatted(uuid));
        }
        return match;
    }

    public OngoingMatch delete(UUID uuid) {
        return matches.remove(uuid);
    }
}
