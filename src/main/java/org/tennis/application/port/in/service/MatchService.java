package org.tennis.application.port.in.service;

import org.tennis.application.dto.MatchDto;
import org.tennis.application.dto.MatchScoreDto;

import java.util.List;


public interface MatchService {

    void create(MatchScoreDto dto);

    List<MatchDto> findComplete();
}
