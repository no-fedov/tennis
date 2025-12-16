package org.tennis.application.port.out.persistence;

import org.tennis.application.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {

    void save(PlayerEntity player);

    Optional<PlayerEntity> findByName(String name);
}
