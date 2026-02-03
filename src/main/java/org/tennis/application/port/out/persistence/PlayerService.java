package org.tennis.application.port.out.persistence;

import org.tennis.application.port.in.service.PlayerCreate;

public interface PlayerService {

    Long create(PlayerCreate dto);
}
