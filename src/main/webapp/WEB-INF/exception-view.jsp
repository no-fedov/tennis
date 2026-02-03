<%@ page    language="java"
            import="org.tennis.application.service.NotFoundException"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<% Throwable exception = (Throwable) request.getAttribute("exception"); %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Tennis | Completed Matches</title>
        <link rel="stylesheet" href="css/styles.css">
        <style>
            main p {
                margin-left: auto
                margin-right: auto
            }
        </style>
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
            <p>
                Error Code: <%= response.getStatus()%>
                <hr>
                <%= exception.getMessage() %>
            </p>
        </main>
    </body>
</html>