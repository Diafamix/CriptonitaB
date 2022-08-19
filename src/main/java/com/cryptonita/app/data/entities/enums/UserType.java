package com.cryptonita.app.data.entities.enums;

public enum UserType {
    FREE(0.9, 50, 100000),
    PREMIUM(0.95, 500, 500000),
    PREMIUM_PLUS(0.98, 1000, 2000000);

    private final double commission;
    private final int rateLimitPerMinute;
    private final int rateLimitPerMonth;

    UserType(double commission, int rateLimitPerMinute, int rateLimitPerMonth) {
        this.commission = commission;
        this.rateLimitPerMinute = rateLimitPerMinute;
        this.rateLimitPerMonth = rateLimitPerMonth;
    }

    public double getCommission() {
        return commission;
    }

    public int getRateLimitPerMinute() {
        return rateLimitPerMinute;
    }

    public int getRateLimitPerMonth() {
        return rateLimitPerMonth;
    }
}
