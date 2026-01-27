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
import java.util.HashMap;
import java.util.List;

import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.PAGE_NUMBER_QUERY_PARAMETER;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.PAGINATION_ANCHOR_ATTRIBUTE;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.calculatePagesCount;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.getPaginationAnchors;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.getValidPageNumber;

@WebServlet(name = "matches", urlPatterns = "/matches")
public class MatchServlet extends HttpServlet {

    private static final Integer PAGE_SIZE = 5;
    public static final String PLAYER_NAME_QUERY_PARAMETER = "filter_by_player_name";
    public static final String COMPLETE_MATCHES_ATTRIBUTE = "completeMatches";

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        this.matchService = (MatchService) getServletContext().getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNumber = req.getParameter(PAGE_NUMBER_QUERY_PARAMETER);
        String playerName = req.getParameter(PLAYER_NAME_QUERY_PARAMETER);
        Long matchesCount = matchService.countComplete(playerName);
        int pagesCount = calculatePagesCount(PAGE_SIZE, matchesCount.intValue());
        Integer validPageNumber = getValidPageNumber(pageNumber, pagesCount);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(PAGE_NUMBER_QUERY_PARAMETER, validPageNumber);
        List<String> paginationAnchors = getPaginationAnchors(req.getServletPath(), validPageNumber, pagesCount, parameters);
        req.setAttribute(PAGINATION_ANCHOR_ATTRIBUTE, paginationAnchors);
        List<MatchDto> completeMatches = matchService.findComplete(PAGE_SIZE, validPageNumber, playerName);
        req.setAttribute(COMPLETE_MATCHES_ATTRIBUTE, completeMatches);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/matches.jsp");
        requestDispatcher.forward(req, resp);
    }
}
