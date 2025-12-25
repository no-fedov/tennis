package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.port.in.service.MatchService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "matches", urlPatterns = "/matches")
public class MatchServlet extends HttpServlet {

    private static final Integer PAGE_SIZE = 20;
    private static final String PAGE_NUMBER_QUERY_PARAMETER = "page";
    private static final String PLAYER_NAME_QUERY_PARAMETER = "filter_by_player_name";

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        this.matchService = (MatchService) getServletContext().getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNumber = req.getParameter(PAGE_NUMBER_QUERY_PARAMETER);
        String playerName = req.getParameter(PLAYER_NAME_QUERY_PARAMETER);
        List<MatchDto> completeMatches = matchService.findComplete(PAGE_SIZE, checkPageNumber(pageNumber), playerName);
        req.setAttribute("completeMatches", completeMatches);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/matches.jsp");
        requestDispatcher.forward(req, resp);
    }

    private Integer checkPageNumber(String pageNumber) {
        if (pageNumber == null) {
            return 1;
        }
        int number = Integer.parseInt(pageNumber);
        if (number <= 0) {
            throw new IllegalArgumentException();
        }
        return number;
    }
}
