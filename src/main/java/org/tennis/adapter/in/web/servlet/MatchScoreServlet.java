package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.model.Match;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.Participant;

import java.io.IOException;
import java.util.Objects;

import static org.tennis.domain.Participant.FIRST;
import static org.tennis.domain.Participant.SECOND;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init() throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        Match matchScoreModel = ongoingMatchesService.get(matchId);
        if (Objects.isNull(matchScoreModel)) {
            throw new IllegalStateException();
        }
        forwardToScoreView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        Match matchScoreModel = ongoingMatchesService.get(matchId);
        if (Objects.isNull(matchScoreModel)) {
            throw new IllegalStateException();
        }
        String pointWinner = req.getParameter("point_winner");
        Participant pointWinnerParticipant = getParticipant(pointWinner);
        matchScoreModel.play(pointWinnerParticipant);
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
