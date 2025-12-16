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
    public List<MatchDto> findComplete() {
        List<MatchEntity> complete = matchRepository.findComplete();
        return matchMapper.toDtos(complete);
    }
}
