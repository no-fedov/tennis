package org.tennis.application.port.in.service;

import org.tennis.application.dto.PlayerDto;

public interface PlayerService {

    PlayerDto create(PlayerCreate dto);
}
