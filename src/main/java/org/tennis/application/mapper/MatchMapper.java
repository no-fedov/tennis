package org.tennis.application.mapper;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.entity.MatchEntity;
import org.tennis.application.entity.PlayerEntity;
import org.tennis.application.port.out.persistence.PlayerRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class MatchMapper {

    private final PlayerRepository playerRepository;

    public MatchEntity toEntity(MatchScoreDto dto) {
        PlayerEntity firstPlayer = playerRepository.findByName(dto.firstPlayerName()).orElseThrow();
        PlayerEntity secondPlayer = playerRepository.findByName(dto.secondPlayerName()).orElseThrow();
        PlayerEntity winner = Objects.equals(dto.winner(), firstPlayer.getName())
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
