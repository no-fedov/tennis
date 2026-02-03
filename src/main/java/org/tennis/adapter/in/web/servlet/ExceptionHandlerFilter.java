package org.tennis.adapter.in.web.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tennis.application.service.NotFoundException;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlerFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (NotFoundException | IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("exception", e);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/exception-view.jsp");
            requestDispatcher.forward(req, res);
        } catch (Throwable e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("exception", new RuntimeException("Server Error"));
        }
    }
}
