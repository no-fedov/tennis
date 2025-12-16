package org.tennis.adapter.out.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.entity.PlayerEntity;
import org.tennis.application.mapper.PlayerMapper;
import org.tennis.application.port.in.service.PlayerService;
import org.tennis.application.port.out.persistence.PlayerRepository;

@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public void create(PlayerDto playerDto) {
        PlayerEntity player = playerMapper.toEntity(playerDto);
        playerRepository.save(player);
    }

    @Override
    public PlayerDto findByName(String name) {
        PlayerEntity player = playerRepository.findByName(name).orElseThrow();
        return playerMapper.toDto(player);
    }
}
