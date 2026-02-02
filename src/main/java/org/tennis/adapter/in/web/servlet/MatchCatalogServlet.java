package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.dto.MatchDto;
import org.tennis.application.port.in.service.MatchService;
import org.tennis.config.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.PAGE_NUMBER_QUERY_PARAMETER;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.PAGINATION_ANCHOR_ATTRIBUTE;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.calculatePagesCount;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.getPaginationAnchors;
import static org.tennis.adapter.in.web.servlet.util.PaginationUtil.getValidPageNumber;

@WebServlet(name = "matches", urlPatterns = "/matches")
public class MatchCatalogServlet extends HttpServlet {

    private static final Integer PAGE_SIZE = 5;
    public static final String PLAYER_NAME_QUERY_PARAMETER = "filter_by_player_name";
    public static final String COMPLETE_MATCHES_ATTRIBUTE = "completedMatches";

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        matchService = context.getMatchService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("id");
        if (Objects.nonNull(matchId)) {
            findById(req, resp);
            return;
        }
        findCompletedMatches(req, resp);
    }

    private void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("id");
        Long id = Long.parseLong(matchId);
        MatchDto completedMatch = matchService.findById(id);
        req.setAttribute("completed-match", completedMatch);
        forwardToCompletedMatch(req, resp);
    }

    private void findCompletedMatches(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pageNumber = req.getParameter(PAGE_NUMBER_QUERY_PARAMETER);
        String playerName = req.getParameter(PLAYER_NAME_QUERY_PARAMETER);
        Long matchesCount = matchService.countComplete(playerName);
        int pagesCount = calculatePagesCount(PAGE_SIZE, matchesCount.intValue());
        Integer validPageNumber = getValidPageNumber(pageNumber, pagesCount);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(PAGE_NUMBER_QUERY_PARAMETER, validPageNumber);
        List<String> paginationAnchors = getPaginationAnchors(req.getServletPath(), validPageNumber, pagesCount, parameters);
        req.setAttribute(PAGINATION_ANCHOR_ATTRIBUTE, paginationAnchors);
        List<MatchDto> completedMatches = matchService.findComplete(PAGE_SIZE, validPageNumber, playerName);
        req.setAttribute(COMPLETE_MATCHES_ATTRIBUTE, completedMatches);
        forwardToCompletedMatches(req, resp);
    }

    private void forwardToCompletedMatches(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/matches.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void forwardToCompletedMatch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/completed-match.jsp");
        requestDispatcher.forward(req, resp);
    }
}
