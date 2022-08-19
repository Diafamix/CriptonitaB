package com.cryptonita.app.exceptions.data;

import com.cryptonita.app.exceptions.LogicError;

public class NoAccountFoundException extends LogicError {

    public NoAccountFoundException() {
    }

    public NoAccountFoundException(String message) {
        super(message);
    }

    public NoAccountFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAccountFoundException(Throwable cause) {
        super(cause);
    }

    public NoAccountFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
