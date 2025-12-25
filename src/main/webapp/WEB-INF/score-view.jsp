<%@ page    language="java"
            import="org.tennis.application.dto.MatchScoreDto"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis | Current Match</title>
</head>

<body>
    <%
        String matchId = request.getParameter("uuid");
        MatchScoreDto score = (MatchScoreDto) request.getAttribute("match_score");
    %>

    <div class="container">
        <div class="inline_block">
            <table>
                <thead>
                    <tr>
                        <td>Player</td>
                        <td>Sets</td>
                        <td>Games</td>
                        <td>
                            <% String pointType = score.tiebreakScore() == null
                                                    ? "Points"
                                                    : "Tiebreak"; %>
                            <%= pointType %>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%= score.firstPlayerName() %></td>
                        <td><%= score.setScore().first() %></td>
                        <td><%= score.gameScore().first() %></td>
                        <td><% if (score.tiebreakScore() == null) {
                                out.print(score.pointScore().first());
                            } else {
                                out.print(score.tiebreakScore().first());
                            }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td><%= score.secondPlayerName() %></td>
                        <td><%= score.setScore().second() %></td>
                        <td><%= score.gameScore().second() %></td>
                        <td><% if (score.tiebreakScore() == null) {
                                out.print(score.pointScore().second());
                            } else {
                                out.print(score.tiebreakScore().second());
                            }
                            %>
                        </td>
                    </tr>
                </tbody>
            </table>

            <% if (!score.isComplete()) { %>
                <form action="/match-score?uuid=<%= matchId %>" method="post">
                    <button type="submit" name="point_winner" value="FIRST">POINT</button>
                    <button type="submit" name="point_winner" value="SECOND">POINT</button>
                </form>
            <% } %>
        </div>
    </div>
</body>
</html>