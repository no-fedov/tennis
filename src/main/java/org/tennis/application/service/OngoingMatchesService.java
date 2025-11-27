package org.tennis.application.service;

import org.tennis.application.model.OngoingMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<String, OngoingMatch> matches = new ConcurrentHashMap<>();

    public String add(OngoingMatch match) {
        String id = UUID.randomUUID().toString();
        matches.put(id, match);
        return id;
    }

    public Optional<OngoingMatch> get(String id) {
        return Optional.ofNullable(matches.get(id));
    }

    public void deleteById(String id) {
        matches.remove(id);
    }
}
