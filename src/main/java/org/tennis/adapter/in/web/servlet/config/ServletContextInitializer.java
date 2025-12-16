package org.tennis.adapter.in.web.servlet.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.tennis.adapter.out.persistence.config.datasource.DataSourceConfig;
import org.tennis.adapter.out.persistence.repository.MatchRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.PlayerRepositoryImpl;
import org.tennis.adapter.out.service.MatchServiceImpl;
import org.tennis.adapter.out.service.PlayerServiceImpl;
import org.tennis.application.mapper.MatchMapper;
import org.tennis.application.mapper.PlayerMapper;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.game.MatchScoreCalculator;

@WebListener
public class ServletContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        EntityManagerFactory entityManagerFactory = DataSourceConfig.getConfig().getEntityManagerFactory();
        MatchScoreCalculator matchScoreCalculator = new MatchScoreCalculator();

        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl(entityManagerFactory);
        PlayerMapper playerMapper = new PlayerMapper();
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository, playerMapper);

        MatchRepositoryImpl matchRepository = new MatchRepositoryImpl(entityManagerFactory);
        MatchMapper matchMapper = new MatchMapper(playerRepository);
        MatchServiceImpl matchService = new MatchServiceImpl(matchRepository, matchMapper);

        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        servletContext.setAttribute("ongoingMatchesService", ongoingMatchesService);
        MatchScoreCalculationService scoreCalculationService = new MatchScoreCalculationService(matchScoreCalculator);
        servletContext.setAttribute("matchScoreCalculationService", scoreCalculationService);
        servletContext.setAttribute("playerService", playerService);
        servletContext.setAttribute("matchService", matchService);
    }
}
