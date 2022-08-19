package com.cryptonita.app.exceptions;

public class LogicError extends RuntimeException {

    public LogicError() {
    }

    public LogicError(String message) {
        super(message);
    }

    public LogicError(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicError(Throwable cause) {
        super(cause);
    }

    public LogicError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
