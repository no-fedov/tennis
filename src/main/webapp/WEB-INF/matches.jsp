<%@ page    language="java"
            import="org.tennis.application.dto.MatchDto, java.util.List"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis | Completed Matches</title>
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
    <header class="nav-panel">
            <div>
                TennisScoreboard
            </div>
            <nav>
                <a href="/">Главная</a>
                <a href="/matches">Завершенные матчи</a>
            </nav>
    </header>
    <main>
        <h1>Completed Matches</h1>
        <form method="GET" action="/matches">
            <input placeholder="Filter by name" type="text" name="filter_by_player_name">
            <button type="submit">Найти</button>
        </form>

    <%
        List<MatchDto> completeMatches = (List<MatchDto>) request.getAttribute("completeMatches");
    %>

    <table class="center">
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
    </main>
</body>
</html>