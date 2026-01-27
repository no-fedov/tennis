package org.tennis.adapter.out.service;

import lombok.RequiredArgsConstructor;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.entity.MatchEntity;
import org.tennis.application.mapper.MatchMapper;
import org.tennis.application.port.in.service.MatchService;
import org.tennis.application.port.out.persistence.MatchRepository;

import java.util.List;

@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Override
    public void create(MatchScoreDto dto) {
        MatchEntity match = matchMapper.toEntity(dto);
        matchRepository.save(match);
    }

    @Override
    public List<MatchDto> findComplete(Integer pageSize, Integer pageNumber, String playerName) {
        List<MatchEntity> complete = matchRepository.findComplete(pageSize, pageNumber, playerName);
        return matchMapper.toDtos(complete);
    }

    @Override
    public Long countComplete(String playerName) {
        return matchRepository.countComplete(playerName);
    }
}
