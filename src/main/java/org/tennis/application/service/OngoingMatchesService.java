package org.tennis.application.service;

import org.tennis.application.model.Match;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<String, Match> matches = new ConcurrentHashMap<>();

    public String add(Match match) {
        String id = UUID.randomUUID().toString();
        matches.put(id, match);
        return id;
    }

    public Match get(String id) {
        return matches.get(id);
    }

    public void deleteById(String id) {
        matches.remove(id);
    }

}
