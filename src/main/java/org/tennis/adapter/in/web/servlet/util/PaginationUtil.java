package org.tennis.adapter.in.web.servlet.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PaginationUtil {

    public static final String PAGE_NUMBER_QUERY_PARAMETER = "page";
    public static final String PAGINATION_ANCHOR_ATTRIBUTE = "paginationAnchor";

    private static final int START_POSITION = 1;
    private static final int MAX_COUNT_PAGE_FOR_SEARCH = 5;
    private static final int SHIFT_FROM_MIDDLE = MAX_COUNT_PAGE_FOR_SEARCH / 2;
    private static final String ANCHOR_TEMPLATE = "<a href=\"%s\"%s>%s</a>";
    private static final String CSS_CLASS_FOR_CURRENT_PAGE = " class=\"current-page\"";

    public static int calculatePagesCount(int pageSize, int countElements) {
        return new BigDecimal(countElements)
                .divide(new BigDecimal(pageSize), BigDecimal.ROUND_UP)
                .intValue();
    }

    public static Integer getValidPageNumber(String pageNumber, int pagesCount) {
        if (pageNumber == null) {
            return START_POSITION;
        }
        try {
            int number = Integer.parseInt(pageNumber);
            if (number > pagesCount) {
                number = pagesCount;
            }
            if (number < START_POSITION) {
                number = START_POSITION;
            }
            return number;
        } catch (NumberFormatException e) {
            return START_POSITION;
        }
    }

    public static List<String> getPaginationAnchors(String path,
                                                    int currentPage,
                                                    int pagesCount,
                                                    Map<String, Object> parameters) {
        List<Integer> numbers = calculatePageNumbers(currentPage, pagesCount);
        if (numbers.size() <= START_POSITION) {
            return List.of();
        }
        List<String> anchors = new ArrayList<>();
        for (Integer pageNumber : numbers) {
            boolean isCurrent = pageNumber == currentPage;
            String link = prepareAnchor(path, pageNumber, parameters, isCurrent);
            anchors.add(link);
        }
        return anchors;
    }

    private static String prepareAnchor(String path, Integer pageNumber, Map<String, Object> parameters, boolean isCurrent) {
        preparePageParameter(pageNumber, parameters);
        String href = buildQueryParameter(path, parameters);
        return ANCHOR_TEMPLATE.formatted(href, isCurrent ? CSS_CLASS_FOR_CURRENT_PAGE : "", pageNumber);
    }

    private static String buildQueryParameter(String path, Map<String, Object> parameters) {
        StringBuilder builder = new StringBuilder(path);
        builder.append("?");
        boolean isFirstParameter = true;
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            if (!isFirstParameter) {
                builder.append("&");
            }
            isFirstParameter = false;
            builder.append(parameter.getKey()).append("=").append(parameter.getValue());
        }
        return builder.toString();
    }

    private static void preparePageParameter(Integer pageNumber, Map<String, Object> parameters) {
        parameters.put("page", pageNumber);
    }

    private static List<Integer> calculatePageNumbers(int currentPage, int amountPages) {
        if (amountPages <= MAX_COUNT_PAGE_FOR_SEARCH) {
            return IntStream.rangeClosed(START_POSITION, amountPages)
                    .boxed()
                    .toList();
        }
        if (currentPage <= SHIFT_FROM_MIDDLE) {
            return IntStream.rangeClosed(START_POSITION, MAX_COUNT_PAGE_FOR_SEARCH)
                    .boxed()
                    .toList();
        }
        if (currentPage + SHIFT_FROM_MIDDLE >= amountPages) {
            return IntStream.rangeClosed(amountPages - MAX_COUNT_PAGE_FOR_SEARCH + START_POSITION, amountPages)
                    .boxed()
                    .toList();
        }
        return IntStream.rangeClosed(currentPage - SHIFT_FROM_MIDDLE, currentPage + SHIFT_FROM_MIDDLE)
                .boxed()
                .toList();
    }
}
