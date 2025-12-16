<%@ page    language="java"
            import="org.tennis.application.dto.MatchDto, java.util.List"
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Matches</title>
</head>
<body>
    <%
        List<MatchDto> completeMatches = (List<MatchDto>) request.getAttribute("completeMatches");
        for (MatchDto match : completeMatches) {
            out.println(match);
        }
    %>
</body>
</html>