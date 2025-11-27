package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.Participant;

import java.io.IOException;

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
        ongoingMatchesService.get(matchId).orElseThrow(() -> new IllegalStateException());
        forwardToScoreView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        String pointWinner = req.getParameter("point_winner");
        Participant pointWinnerParticipant = getParticipant(pointWinner);
        ongoingMatchesService.get(matchId).ifPresentOrElse(match -> match.play(pointWinnerParticipant),
                () -> {
                    throw new IllegalStateException();
                });
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
