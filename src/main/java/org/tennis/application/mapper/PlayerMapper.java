package org.tennis.application.mapper;

import org.tennis.application.dto.PlayerDto;
import org.tennis.application.entity.PlayerEntity;

public class PlayerMapper {

    public PlayerEntity toEntity(PlayerDto dto) {
        return new PlayerEntity(dto.name());
    }

    public PlayerDto toDto(PlayerEntity entity) {
        return new PlayerDto(entity.getName());
    }
}
