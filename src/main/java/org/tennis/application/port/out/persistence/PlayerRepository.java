package org.tennis.application.port.out.persistence;

import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.port.in.service.PlayerCreate;

import java.util.Optional;

public interface PlayerRepository {

    PlayerDto save(PlayerCreate player);

    Optional<PlayerDto> findById(Long id);

    Optional<PlayerDto> findByName(String name);

    Optional<PlayerEntity> findPlayerById(Long id);
}
