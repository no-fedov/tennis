package org.tennis.application.port.in.service;

import org.tennis.application.dto.PlayerDto;

public interface PlayerService {

    void create(PlayerDto dto);

    PlayerDto findByName(String name);
}
