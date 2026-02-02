package org.tennis.adapter.out.persistence.repository.mapper;

import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.port.in.service.PlayerCreate;

import java.util.Objects;

public class PlayerMapper {

    public PlayerEntity toEntity(PlayerCreate dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return new PlayerEntity(dto.name());
    }

    public PlayerDto toDto(PlayerEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return new PlayerDto(entity.getId(), entity.getName());
    }
}
