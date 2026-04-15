package com.personalresume.common;

import org.springframework.data.domain.Sort;

public final class SortUtils {

    private SortUtils() {
    }

    public static Sort bySortOrderAndUpdatedAt() {
        return Sort.by(Sort.Order.asc("sortOrder"), Sort.Order.desc("updatedAt"));
    }

    public static Sort byUpdatedAtDesc() {
        return Sort.by(Sort.Order.desc("updatedAt"));
    }
}
