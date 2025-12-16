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

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        this.matchService = (MatchService) getServletContext().getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MatchDto> completeMatches = matchService.findComplete();
        req.setAttribute("completeMatches", completeMatches);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/matches.jsp");
        requestDispatcher.forward(req, resp);
    }
}
