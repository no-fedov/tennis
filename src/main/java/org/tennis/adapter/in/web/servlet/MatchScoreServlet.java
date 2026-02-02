package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.dto.MatchScoreDto;
import org.tennis.application.model.OngoingMatch;
import org.tennis.application.port.in.service.MatchCompletedCreate;
import org.tennis.application.port.in.service.MatchPlayUseCase;
import org.tennis.application.port.in.service.MatchScoreUseCase;
import org.tennis.application.port.in.service.MatchService;
import org.tennis.config.ApplicationContext;
import org.tennis.domain.game.Participant;

import java.io.IOException;
import java.util.UUID;

import static org.tennis.domain.game.Participant.getParticipant;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private MatchScoreUseCase matchScoreUseCase;
    private MatchPlayUseCase matchPlayUseCase;
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        matchScoreUseCase = context.getMatchScoreUseCase();
        matchPlayUseCase = context.getMatchPlayUseCase();
        matchService = context.getMatchService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchUUID = req.getParameter("uuid");
        UUID uuid = UUID.fromString(matchUUID);
        MatchScoreDto matchScoreDto = matchScoreUseCase.calculate(uuid);
        req.setAttribute("match_score", matchScoreDto);
        forwardToScoreView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchUUID = req.getParameter("uuid");
        String pointWinner = req.getParameter("point_winner");
        UUID uuid = UUID.fromString(matchUUID);
        Participant pointWinnerParticipant = getParticipant(pointWinner);
        OngoingMatch ongoingMatch = matchPlayUseCase.play(uuid, pointWinnerParticipant);
        MatchScoreDto matchScoreDto = matchScoreUseCase.calculate(ongoingMatch);
        req.setAttribute("match_score", matchScoreDto);
        if (ongoingMatch.isComplete()) {
            MatchCompletedCreate completedMatch = new MatchCompletedCreate(ongoingMatch.getFirstPlayerId(),
                    ongoingMatch.getSecondPlayerId(),
                    ongoingMatch.getWinnerId());
            Long idCompletedMatch = matchService.create(completedMatch);
            resp.sendRedirect(String.format("/matches?id=%s", idCompletedMatch));
            return;
        }
        resp.sendRedirect(String.format("/match-score?uuid=%s", uuid));
    }

    private void forwardToScoreView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/score-view.jsp");
        requestDispatcher.forward(req, resp);
    }
}
