<%@ page    language="java"
            import="org.tennis.application.dto.MatchDto, java.util.List"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis | Matches</title>
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
    <header class="home-page-nav">
            <div>
                TennisScoreboard
            </div>
            <nav>
                <a href="/">Главная</a>
                <a href="/matches">Завершенные матчи</a>
            </nav>
    </header>
    <main>
    <%
        List<MatchDto> completeMatches = (List<MatchDto>) request.getAttribute("completeMatches");
        for (MatchDto match : completeMatches) {
            out.println(match);
        }
    %>
    </main>
</body>
</html>