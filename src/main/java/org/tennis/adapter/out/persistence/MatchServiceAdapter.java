package org.tennis.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.tennis.adapter.out.persistence.entity.MatchEntity;
import org.tennis.adapter.out.persistence.repository.MatchRepository;
import org.tennis.adapter.out.persistence.repository.mapper.MatchMapper;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.port.out.persistence.MatchCompletedCreate;
import org.tennis.application.port.out.persistence.MatchService;

import java.util.List;

@RequiredArgsConstructor
public class MatchServiceAdapter implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Override
    public void create(MatchCompletedCreate dto) {
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
