package com.cryptonita.app.core.controllers.utils;

import com.cryptonita.app.core.utils.Validate;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseAdviceImpl implements ResponseBodyAdvice<RestResponse> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == RestResponse.class;
    }

    @Override
    public RestResponse beforeBodyWrite(RestResponse body,
                                        MethodParameter returnType,
                                        MediaType selectedContentType,
                                        Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                        ServerHttpRequest request,
                                        ServerHttpResponse response) {
        TokenConsume consume;
        if ((consume = getTokens(returnType)) != null)
            body.status.credit_count = consume.value();

        body.status.elapsed = getElapsedTime((ServletServerHttpRequest) request);

        return body;
    }

    private TokenConsume getTokens(MethodParameter returnType) {
        return Validate.testOrGet(() -> returnType.getMethod().getAnnotation(TokenConsume.class), null);
    }

    private long getElapsedTime(ServletServerHttpRequest request) {
        long startTime = (long) request.getServletRequest().getAttribute("startTime");
        return System.currentTimeMillis() - startTime;
    }

}
