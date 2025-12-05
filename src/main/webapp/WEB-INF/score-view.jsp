<%@ page    language="java"
            import="org.tennis.application.dto.MatchScoreDto"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Match</title>
</head>
<body>
    <%
        String matchId = request.getParameter("uuid");
        MatchScoreDto score = (MatchScoreDto) request.getAttribute("match_score");
    %>

    <table>
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
        </tr>
    </table>

    <form action="/match-score?uuid=<%= matchId %>" method="post">
        <button type="submit" name="point_winner" value="FIRST">POINT</button>
        <button type="submit" name="point_winner" value="SECOND">POINT</button>
    </form>

</body>
</html>