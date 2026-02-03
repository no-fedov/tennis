package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tennis.application.dto.MatchScoreDto;

import java.io.IOException;

@WebServlet(urlPatterns = "/completed-match")
public class CompletedMatchViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MatchScoreDto matchScore = (MatchScoreDto) session.getAttribute("match_score");
        if (matchScore == null) {
            throw new IllegalArgumentException();
        }
        session.removeAttribute("match_score");
        req.setAttribute("match_score", matchScore);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/completed-match.jsp");
        requestDispatcher.forward(req, resp);
    }
}
