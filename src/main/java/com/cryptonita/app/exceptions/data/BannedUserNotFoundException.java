package com.cryptonita.app.exceptions.data;

import com.cryptonita.app.exceptions.LogicError;

public class BannedUserNotFoundException extends LogicError {

    public BannedUserNotFoundException() {
    }

    public BannedUserNotFoundException(String message) {
        super(message);
    }

    public BannedUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BannedUserNotFoundException(Throwable cause) {
        super(cause);
    }

    public BannedUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
