package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.dto.PlayerDto;
import org.tennis.application.model.OngoingMatch;
import org.tennis.application.port.in.service.PlayerService;
import org.tennis.application.service.OngoingMatchesService;
import org.tennis.domain.game.Match;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "newMatch", urlPatterns = "/new-match")
public class CreateMatchServlet extends HttpServlet {

    private static final String FIRST_PLAYER_PARAMETER = "first_player";
    private static final String SECOND_PLAYER_PARAMETER = "second_player";
    private static final Pattern PATTERN_FOR_NAME = Pattern.compile("^[\\p{L}\\s]{1,20}$", Pattern.UNICODE_CHARACTER_CLASS);

    private OngoingMatchesService ongoingMatchesService;
    private PlayerService playerService;

    @Override
    public void init() throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        this.playerService = (PlayerService) getServletContext().getAttribute("playerService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter(FIRST_PLAYER_PARAMETER);
        String secondPlayerName = req.getParameter(SECOND_PLAYER_PARAMETER);
        validateName(firstPlayerName);
        validateName(secondPlayerName);
        createOrFindPlayer(firstPlayerName);
        createOrFindPlayer(secondPlayerName);
        OngoingMatch match = new OngoingMatch(firstPlayerName, secondPlayerName, new Match());
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

    private void createOrFindPlayer(String name) {
        playerService.findByName(name)
                .ifPresentOrElse(e -> {}, () -> playerService.create(new PlayerDto(name)));
    }
}
