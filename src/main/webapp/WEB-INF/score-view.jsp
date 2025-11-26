<%@ page    language="java"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Match</title>
</head>
<body>
    <%
        String matchId = request.getParameter("uuid");
    %>

    <form action="/match-score?uuid=<%= matchId %>" method="post">
        <button type="submit" name="point_winner" value="FIRST">POINT</button>
    </form>

    <form action="/match-score?uuid=<%= matchId %>" method="post">
        <button type="submit" name="point_winner" value="SECOND">POINT</button>
    </form>
</body>
</html>