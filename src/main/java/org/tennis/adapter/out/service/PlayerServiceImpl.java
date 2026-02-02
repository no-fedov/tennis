package org.tennis.adapter.out.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.port.in.service.PlayerCreate;
import org.tennis.application.port.in.service.PlayerService;
import org.tennis.application.port.out.persistence.PlayerRepository;

@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerDto create(PlayerCreate playerDto) {
        return playerRepository.save(playerDto);
    }
}
