package org.tennis.application.port.out.persistence;

import org.tennis.application.entity.MatchEntity;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    void save(MatchEntity match);

    Optional<MatchEntity> findById(Long id);

    List<MatchEntity> findComplete(Integer pageSize, Integer pageNumber, String playerName);

    Long countComplete(String playerName);
}
