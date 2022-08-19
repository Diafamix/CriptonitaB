package com.cryptonita.app.exceptions.data;

import com.cryptonita.app.exceptions.LogicError;

public class CoinNotFoundException extends LogicError {

    public CoinNotFoundException() {
    }

    public CoinNotFoundException(String message) {
        super(message);
    }

    public CoinNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoinNotFoundException(Throwable cause) {
        super(cause);
    }

    public CoinNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
