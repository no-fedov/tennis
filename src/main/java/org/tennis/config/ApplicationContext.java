package org.tennis.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.tennis.adapter.out.persistence.config.datasource.DataSourceConfig;
import org.tennis.adapter.out.persistence.repository.MatchRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.PlayerRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.mapper.MatchMapper;
import org.tennis.adapter.out.persistence.repository.mapper.PlayerMapper;
import org.tennis.adapter.out.service.MatchServiceImpl;
import org.tennis.adapter.out.service.PlayerServiceImpl;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.application.port.in.service.MatchScoreUseCase;
import org.tennis.application.port.in.service.MatchService;
import org.tennis.application.port.in.service.PlayerService;
import org.tennis.application.port.out.persistence.MatchRepository;
import org.tennis.application.port.out.persistence.PlayerRepository;
import org.tennis.application.service.MatchPlayService;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.game.MatchScoreCalculator;

@Getter
public class ApplicationContext {
    private final MatchService matchService;
    private final MatchPlayUseCase matchPlayUseCase;
    private final MatchScoreUseCase matchScoreUseCase;
    private final PlayerService playerService;

    private final OngoingMatchesService ongoingMatchesService;


    public ApplicationContext() {
        EntityManagerFactory emf = DataSourceConfig.getConfig().getEntityManagerFactory();
        PlayerMapper playerMapper = new PlayerMapper();
        PlayerRepository playerRepository = new PlayerRepositoryImpl(emf, playerMapper);
        MatchMapper matchMapper = new MatchMapper(playerRepository);
        MatchRepository matchRepository = new MatchRepositoryImpl(emf);
        this.matchService = new MatchServiceImpl(matchRepository, matchMapper);

        ongoingMatchesService = new OngoingMatchesService();

        this.matchPlayUseCase = new MatchPlayService(ongoingMatchesService);
        MatchScoreCalculator matchScoreCalculator = new MatchScoreCalculator();

        this.matchScoreUseCase = new MatchScoreCalculationService(matchScoreCalculator, ongoingMatchesService);
        this.playerService = new PlayerServiceImpl(playerRepository);


    }
}
