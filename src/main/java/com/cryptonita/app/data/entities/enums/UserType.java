package com.cryptonita.app.data.entities.enums;

public enum UserType {
    FREE(0.9),
    PREMIUM(0.95),
    PREMIUM_PLUS(0.98);


    private final double comission;

    UserType(double comission) {
        this.comission = comission;
    }

    public double getComission() {
        return comission;
    }
}
