package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.model.Match;
import org.tennis.application.service.OngoingMatchesService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "newMatch", urlPatterns = "/new-match")
public class CreateMatchServlet extends HttpServlet {

    private static final String FIRST_PLAYER_PARAMETER = "first_player";
    private static final String SECOND_PLAYER_PARAMETER = "second_player";
    private static final Pattern PATTERN_FOR_NAME = Pattern.compile("^\\w{5,20}$");
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init() throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter(FIRST_PLAYER_PARAMETER);
        String secondPlayerName = req.getParameter(SECOND_PLAYER_PARAMETER);
        validateName(firstPlayerName);
        validateName(secondPlayerName);
        // TODO: сохранить или получить id
        Match match = new Match(firstPlayerName, secondPlayerName, new org.tennis.domain.Match());
        String matchId = ongoingMatchesService.add(match);
        resp.sendRedirect(String.format("/match-score?uuid=%s", matchId));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/create-match.html");
        requestDispatcher.forward(req, resp);
    }

    private void validateName(String name) {
        Matcher matcher = PATTERN_FOR_NAME.matcher(name);
        boolean matches = matcher.matches();
        if (!matches) {
            throw new IllegalArgumentException();
        }
    }
}
