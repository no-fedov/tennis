package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.game.MatchScoreCalculator;

@WebListener
public class ServletContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        MatchScoreCalculator matchScoreCalculator = new MatchScoreCalculator();
        servletContext.setAttribute("ongoingMatchesService", new OngoingMatchesService());
        servletContext.setAttribute("matchScoreCalculationService", new MatchScoreCalculationService(matchScoreCalculator));
    }
}
