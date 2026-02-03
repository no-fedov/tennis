package org.tennis.adapter.out.persistence.repository;

import org.tennis.adapter.out.persistence.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {

    PlayerEntity save(PlayerEntity player);

    Optional<PlayerEntity> findById(Long id);

    Optional<PlayerEntity> findByName(String name);

}
