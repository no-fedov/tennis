package org.tennis.application.port.in.service;

import org.tennis.application.dto.PlayerDto;

import java.util.Optional;

public interface PlayerService {

    void create(PlayerDto dto);

    Optional<PlayerDto> findByName(String name);
}
