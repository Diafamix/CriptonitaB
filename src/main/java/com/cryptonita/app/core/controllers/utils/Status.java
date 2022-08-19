package com.cryptonita.app.core.controllers.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

class Status {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public final LocalDateTime timestamp;
    public final int error_code;
    public final String error_message;
    public final double elapsed;
    public final int credit_count;

    public Status(int error_code, String error_message) {
        this.timestamp = LocalDateTime.now();
        this.error_code = error_code;
        this.error_message = error_message;
        this.elapsed = 0;
        this.credit_count = 0;
    }

    public Status(int error_code, String error_message, double elapsed) {
        this.timestamp = LocalDateTime.now();
        this.error_code = error_code;
        this.error_message = error_message;
        this.elapsed = elapsed;
        this.credit_count = 0;
    }

    public Status(int error_code, String error_message, int credit_count) {
        this.timestamp = LocalDateTime.now();
        this.error_code = error_code;
        this.error_message = error_message;
        this.elapsed = 0;
        this.credit_count = credit_count;
    }

    public Status(int error_code, String error_message, double elapsed, int credit_count) {
        this.timestamp = LocalDateTime.now();
        this.error_code = error_code;
        this.error_message = error_message;
        this.elapsed = elapsed;
        this.credit_count = credit_count;
    }

}
