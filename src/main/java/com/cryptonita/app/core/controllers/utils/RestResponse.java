package com.cryptonita.app.core.controllers.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class RestResponse {

    public static RestResponse encapsulate(Exception e, HttpStatus status) {
        return new RestResponse(e, status);
    }

    public static RestResponse encapsulate(Object data) {
        return new RestResponse(data);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final Object data;
    public final Status status;

    public RestResponse(Object data) {
        this.data = data;
        this.status = new Status(0, "");
    }

    public RestResponse(Exception e, HttpStatus status) {
        this.data = null;
        this.status = new Status(status.value(), e.getMessage());
    }

}
