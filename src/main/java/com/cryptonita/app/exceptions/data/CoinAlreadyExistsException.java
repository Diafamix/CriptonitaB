package com.cryptonita.app.exceptions.data;

import com.cryptonita.app.exceptions.LogicError;

public class CoinAlreadyExistsException extends LogicError {

    public CoinAlreadyExistsException() {
    }

    public CoinAlreadyExistsException(String message) {
        super(message);
    }

    public CoinAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoinAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CoinAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
