package org.tennis.application.port.in.service;

import org.tennis.application.dto.MatchDto;
import org.tennis.application.dto.MatchScoreDto;

import java.util.List;


public interface MatchService {

    Long create(MatchScoreDto dto);

    MatchDto findById(Long id);

    List<MatchDto> findComplete(Integer pageSize, Integer pageNumber, String playerName);

    Long countComplete(String playerName);
}
