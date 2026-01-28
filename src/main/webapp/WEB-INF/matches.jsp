<%@ page    language="java"
            import="org.tennis.application.dto.MatchDto, org.tennis.adapter.in.web.servlet.MatchServlet, java.util.List"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<%
    List<MatchDto> completeMatches = (List<MatchDto>) request.getAttribute("completedMatches");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Tennis | Completed Matches</title>
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <header>
            <span class="nav-description">TennisScoreboard</span>
            <nav>
                <a class="nav-button-link" href="/">Домой</a>
                <a class="nav-button-link" href="/matches">Матчи</a>
            </nav>
        </header>

        <main>
            <div class="table-caption">
                <h1>Completed Matches</h1>

                <form method="GET" action="/matches">
                    <input class="player-name-input"
                        placeholder="Filter by name"
                        type="text"
                        name="filter_by_player_name">
                    <button class="button-link" type="submit">Найти</button>
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <td>First Player</td>
                        <td>Second Player</td>
                        <td>Winner</td>
                    </tr>
                </thead>
                <tbody>
                        <%
                            for (MatchDto match : completeMatches) {
                        %>
                            <tr>
                              <td><%= match.firstPlayerName() %></td>
                              <td><%= match.secondPlayerName() %></td>
                              <td><%= match.winner() %></td>
                            </tr>
                        <%
                            }
                        %>
                </tbody>
            </table>
            <%@include file="pagination.jsp" %>
        </main>
    </body>
</html>