package com.cryptonita.app.core.controllers.utils;

import com.cryptonita.app.exceptions.LogicError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected RestResponse badRequestHandler(ConstraintViolationException e) {
        return RestResponse.encapsulate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected RestResponse authenticationHandler(BadCredentialsException e) {
        return RestResponse.encapsulate(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LogicError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected RestResponse logicErrorHandler(LogicError e) {
        return RestResponse.encapsulate(e, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected RestResponse defaultHandler(RuntimeException e) {
        return RestResponse.encapsulate("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.ok(RestResponse.encapsulate(ex, status));
    }

}