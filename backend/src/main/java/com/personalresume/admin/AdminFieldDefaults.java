package com.personalresume.admin;

public final class AdminFieldDefaults {

    private AdminFieldDefaults() {
    }

    public static int sortOrder(Integer value) {
        return value == null ? 0 : Math.max(value, 0);
    }

    public static boolean isVisible(Boolean value) {
        return value == null || value;
    }
}
