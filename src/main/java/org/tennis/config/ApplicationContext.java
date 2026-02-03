package org.tennis.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.tennis.adapter.out.persistence.MatchServiceAdapter;
import org.tennis.adapter.out.persistence.PlayerServiceAdapter;
import org.tennis.adapter.out.persistence.config.datasource.DataSourceConfig;
import org.tennis.adapter.out.persistence.repository.MatchRepository;
import org.tennis.adapter.out.persistence.repository.MatchRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.PlayerRepository;
import org.tennis.adapter.out.persistence.repository.PlayerRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.mapper.MatchMapper;
import org.tennis.adapter.out.persistence.repository.mapper.PlayerMapper;
import org.tennis.application.port.in.service.CreateOngoingMatchUseCase;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.application.port.in.service.MatchScoreUseCase;
import org.tennis.application.port.out.persistence.MatchService;
import org.tennis.application.port.out.persistence.PlayerService;
import org.tennis.application.service.CreateOngoingMatchService;
import org.tennis.application.service.MatchPlayService;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.storage.OngoingMatchesInMemoryStorage;
import org.tennis.domain.game.MatchScoreCalculator;

@Getter
public class ApplicationContext {
    private final MatchService matchService;
    private final MatchPlayUseCase matchPlayUseCase;
    private final MatchScoreUseCase matchScoreUseCase;
    private final PlayerService playerService;
    private final CreateOngoingMatchUseCase createOngoingMatchUseCase;

    public ApplicationContext() {
        EntityManagerFactory emf = DataSourceConfig.getConfig().getEntityManagerFactory();
        PlayerMapper playerMapper = new PlayerMapper();
        PlayerRepository playerRepository = new PlayerRepositoryImpl(emf);
        this.playerService = new PlayerServiceAdapter(playerRepository, playerMapper);

        MatchMapper matchMapper = new MatchMapper(playerRepository);
        MatchRepository matchRepository = new MatchRepositoryImpl(emf);
        this.matchService = new MatchServiceAdapter(matchRepository, matchMapper);

        OngoingMatchesInMemoryStorage ongoingMatchesInMemoryStorage = new OngoingMatchesInMemoryStorage();
        this.createOngoingMatchUseCase = new CreateOngoingMatchService(ongoingMatchesInMemoryStorage, playerService);
        MatchScoreCalculator matchScoreCalculator = new MatchScoreCalculator();

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(matchScoreCalculator, ongoingMatchesInMemoryStorage);
        this.matchScoreUseCase = matchScoreCalculationService;
        this.matchPlayUseCase = new MatchPlayService(ongoingMatchesInMemoryStorage, matchScoreCalculationService, matchService);
    }
}
