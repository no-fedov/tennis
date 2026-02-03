package org.tennis.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.adapter.out.persistence.repository.PlayerRepository;
import org.tennis.adapter.out.persistence.repository.mapper.PlayerMapper;
import org.tennis.application.port.in.service.PlayerCreate;
import org.tennis.application.port.out.persistence.PlayerService;

@RequiredArgsConstructor
public class PlayerServiceAdapter implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public Long create(PlayerCreate playerCreateDto) {
        PlayerEntity player = playerMapper.toEntity(playerCreateDto);
        playerRepository.save(player);
        return player.getId();
    }
}
