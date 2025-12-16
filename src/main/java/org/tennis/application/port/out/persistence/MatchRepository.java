package org.tennis.application.port.out.persistence;

import org.tennis.application.entity.MatchEntity;

import java.util.List;

public interface MatchRepository {

    void save(MatchEntity match);

    List<MatchEntity> findComplete();
}
