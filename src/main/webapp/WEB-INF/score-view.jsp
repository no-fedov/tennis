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
            <td>Points</td>
        </tr>
        <tr>
            <td><%= score.firstPlayerName() %></td>
            <td><%= score.setScore().first() %></td>
            <td><%= score.gameScore().first() %></td>
            <td><%= score.pointScore().first() %></td>
        </tr>
        <tr>
            <td><%= score.secondPlayerName() %></td>
            <td><%= score.setScore().second() %></td>
            <td><%= score.gameScore().second() %></td>
            <td><%= score.pointScore().second() %></td>
        </tr>
    </table>

    <form action="/match-score?uuid=<%= matchId %>" method="post">
        <button type="submit" name="point_winner" value="FIRST">POINT</button>
    </form>

    <form action="/match-score?uuid=<%= matchId %>" method="post">
        <button type="submit" name="point_winner" value="SECOND">POINT</button>
    </form>
</body>
</html>