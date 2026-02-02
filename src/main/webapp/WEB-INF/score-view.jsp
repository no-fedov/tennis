<%@ page    language="java"
            import="org.tennis.application.dto.MatchScoreDto"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <title>Tennis | Current Match</title>
</head>

<%
    String matchId = request.getParameter("uuid");
    MatchScoreDto score = (MatchScoreDto) request.getAttribute("match_score");
%>

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
                    <td>Player</td>
                    <td>Sets</td>
                    <td>Games</td>
                    <td>
                        <% String pointType = score.tiebreakScore() == null
                                                ? "Points"
                                                : "Tiebreak";
                        %>
                        <%= pointType %>
                    </td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= score.firstPlayerName() %></td>
                    <td><%= score.setScore().first() %></td>
                    <td><%= score.gameScore().first() %></td>
                    <td>
                        <% if (score.tiebreakScore() == null) {
                            out.print(score.pointScore().first());
                        } else {
                            out.print(score.tiebreakScore().first());
                        }
                        %>
                    </td>
                    <td>
                        <button class="button-link" form="point" type="submit" name="point_winner" value="FIRST">POINT</button>
                    </td>
                </tr>
                <tr>
                    <td><%= score.secondPlayerName() %></td>
                    <td><%= score.setScore().second() %></td>
                    <td><%= score.gameScore().second() %></td>
                    <td>
                        <% if (score.tiebreakScore() == null) {
                            out.print(score.pointScore().second());
                        } else {
                            out.print(score.tiebreakScore().second());
                        }
                        %>
                    </td>
                    <td>
                        <button class="button-link" form="point" type="submit" name="point_winner" value="SECOND">POINT</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <form id="point" action="/match-score?uuid=<%= matchId %>" method="post" style="display:none">
        </form>
    </main>
</body>
</html>