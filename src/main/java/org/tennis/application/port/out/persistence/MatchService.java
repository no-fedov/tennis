package org.tennis.application.port.out.persistence;

import org.tennis.application.dto.MatchDto;

import java.util.List;


public interface MatchService {

    Long create(MatchCompletedCreate dto);

    List<MatchDto> findComplete(Integer pageSize, Integer pageNumber, String playerName);

    Long countComplete(String playerName);
}
