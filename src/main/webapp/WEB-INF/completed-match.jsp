<%@ page    language="java"
            import="org.tennis.application.dto.MatchScoreDto"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<% MatchScoreDto match = (MatchScoreDto) request.getAttribute("match_score"); %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>TennisScoreboard | Match Complete</title>
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
            <table>
                <thead>
                    <tr>
                        <td>First Player</td>
                        <td>Second Player</td>
                        <td>Winner</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                      <td><%= match.firstPlayerName() %></td>
                      <td><%= match.secondPlayerName() %></td>
                      <td><%= match.winner() %></td>
                    </tr>
                </tbody>
            </table>
        </main>
    </body>
</html>