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
        <h1>Completed Matches</h1>
        <form method="GET" action="/matches">
            <input placeholder="Filter by name" type="text" name="filter_by_player_name">
            <button type="submit">Найти</button>
        </form>

    <%
        List<MatchDto> completeMatches = (List<MatchDto>) request.getAttribute("completeMatches");
    %>

        <table>
            <thead>
                <tr>
                    <th>First Player</td>
                    <th>Second Player</td>
                    <th>Winner</td>
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