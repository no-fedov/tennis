package org.tennis.adapter.out.persistence.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.tennis.adapter.out.persistence.entity.MatchEntity;
import org.tennis.adapter.out.persistence.entity.PlayerEntity;
import org.tennis.adapter.out.persistence.repository.PlayerRepository;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.port.out.persistence.MatchCompletedCreate;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class MatchMapper {

    private final PlayerRepository playerRepository;

    public MatchEntity toEntity(MatchCompletedCreate match) {
        PlayerEntity firstPlayer = playerRepository.findById(match.firstPlayerId()).orElseThrow();
        PlayerEntity secondPlayer = playerRepository.findById(match.secondPlayerId()).orElseThrow();
        PlayerEntity winner = Objects.equals(match.winnerId(), firstPlayer.getId())
                ? firstPlayer
                : secondPlayer;
        return new MatchEntity(firstPlayer, secondPlayer, winner);
    }

    public MatchDto toDto(MatchEntity entity) {
        return new MatchDto(
                entity.getId(),
                entity.getFirstPlayer().getName(),
                entity.getSecondPlayer().getName(),
                entity.getWinner().getName()
        );
    }

    public List<MatchDto> toDtos(List<MatchEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
