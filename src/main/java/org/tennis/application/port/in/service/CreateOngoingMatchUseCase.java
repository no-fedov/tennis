package org.tennis.application.port.in.service;

import java.util.UUID;

public interface CreateOngoingMatchUseCase {

    UUID create(String firstPlayerName, String secondPlayerName);
}
