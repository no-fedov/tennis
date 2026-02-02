package org.tennis.adapter.in.web.servlet.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.tennis.adapter.out.persistence.config.datasource.DataSourceConfig;
import org.tennis.adapter.out.persistence.repository.MatchRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.PlayerRepositoryImpl;
import org.tennis.adapter.out.persistence.repository.mapper.MatchMapper;
import org.tennis.adapter.out.persistence.repository.mapper.PlayerMapper;
import org.tennis.adapter.out.service.MatchServiceImpl;
import org.tennis.adapter.out.service.PlayerServiceImpl;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.config.ApplicationContext;
import org.tennis.domain.game.MatchScoreCalculator;

@WebListener
public class ServletContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("context", new ApplicationContext());
    }
}
