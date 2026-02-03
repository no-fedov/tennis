package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.port.in.service.CreateOngoingMatchUseCase;
import org.tennis.config.ApplicationContext;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "newMatch", urlPatterns = "/new-match")
public class OngoingMatchCreateServlet extends HttpServlet {

    private static final String FIRST_PLAYER_PARAMETER = "first_player";
    private static final String SECOND_PLAYER_PARAMETER = "second_player";
    private static final Pattern PATTERN_FOR_NAME = Pattern.compile("^[\\p{L}\\s]{1,20}$", Pattern.UNICODE_CHARACTER_CLASS);

    private CreateOngoingMatchUseCase createOngoingMatchUseCase;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        createOngoingMatchUseCase = context.getCreateOngoingMatchUseCase();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/create-match.html");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter(FIRST_PLAYER_PARAMETER);
        String secondPlayerName = req.getParameter(SECOND_PLAYER_PARAMETER);
        validateNames(firstPlayerName, secondPlayerName);
        UUID uuid = createOngoingMatchUseCase.create(firstPlayerName, secondPlayerName);
        resp.sendRedirect(String.format("/match-score?uuid=%s", uuid));
    }

    private void validateNames(String... names) {
        for (String name : names) {
            Matcher matcher = PATTERN_FOR_NAME.matcher(name);
            boolean matches = matcher.matches();
            if (!matches) {
                throw new IllegalArgumentException();
            }
        }
    }
}
