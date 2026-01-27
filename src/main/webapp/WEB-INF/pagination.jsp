<%@ page    language="java"
            import="java.util.List, org.tennis.adapter.in.web.servlet.util.PaginationUtil"
            contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"
%>

<%
    List<String> anchors = (List<String>) request.getAttribute(PaginationUtil.PAGINATION_ANCHOR_ATTRIBUTE);
%>
    <div class="pagination">
<%
        for (String anchor : anchors) {
            out.println(anchor);
        }
%>
    </div>
