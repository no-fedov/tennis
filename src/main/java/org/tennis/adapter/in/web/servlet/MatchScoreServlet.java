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
import org.tennis.application.port.in.service.MatchService;
import org.tennis.application.service.MatchScoreCalculationService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.game.Participant;

import java.io.IOException;

import static org.tennis.domain.game.Participant.FIRST;
import static org.tennis.domain.game.Participant.SECOND;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.ongoingMatchesService = (OngoingMatchesService) context.getAttribute("ongoingMatchesService");
        this.matchScoreCalculationService = (MatchScoreCalculationService) context.getAttribute("matchScoreCalculationService");
        this.matchService = (MatchService) context.getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        OngoingMatch ongoingMatch = ongoingMatchesService.get(matchId)
                .orElseThrow(() -> new NotFoundException("Match complete or not exist"));
        MatchScoreDto matchScoreDto = matchScoreCalculationService.calculatePointGameScore(ongoingMatch);
        req.setAttribute("match_score", matchScoreDto);
        forwardToScoreView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        String pointWinner = req.getParameter("point_winner");
        Participant pointWinnerParticipant = getParticipant(pointWinner);
        OngoingMatch ongoingMatch = ongoingMatchesService.get(matchId)
                .orElseThrow(() -> new NotFoundException("Match complete or not exist"));
        ongoingMatch.play(pointWinnerParticipant);
        MatchScoreDto matchScoreDto = matchScoreCalculationService.calculatePointGameScore(ongoingMatch);
        req.setAttribute("match_score", matchScoreDto);
        if (ongoingMatch.isComplete()) {
            ongoingMatchesService.deleteById(matchId);
            matchService.create(matchScoreDto);
        }
//        resp.setStatus(HttpServletResponse.SC_CREATED);
        forwardToScoreView(req, resp);
    }

    private void forwardToScoreView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/score-view.jsp");
        requestDispatcher.forward(req, resp);
    }

    private Participant getParticipant(String string) {
        return switch (string) {
            case "FIRST" -> FIRST;
            case "SECOND" -> SECOND;
            default -> throw new IllegalStateException();
        };
    }
}
