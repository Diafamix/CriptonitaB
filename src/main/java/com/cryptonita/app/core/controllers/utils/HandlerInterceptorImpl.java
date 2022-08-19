package com.cryptonita.app.core.controllers.utils;

import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import com.cryptonita.app.security.utils.RatePerMinuteMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Service
@AllArgsConstructor
public class HandlerInterceptorImpl implements HandlerInterceptor {

    private final RatePerMinuteMapService mapService;
    private final SecurityContextHelper securityContextHelper;
    private final IUserProvider userProvider;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception
    {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Method method;
        if ((method = getMethodName(handler)) == null) return;

        TokenConsume consume = method.getAnnotation(TokenConsume.class);
        if (consume == null) return;

        UserResponseDTO dto = securityContextHelper.getUser();
        mapService.consume(dto, consume.value());

        userProvider.changeUserNumRequests(dto.getUsername());

        addTokenToRestResponse(handler, consume.value());
        logTimeElapsed(request);

        log.info("Tokens of user: " + mapService.getTokenConsumed(dto));
    }

    private void logTimeElapsed(HttpServletRequest request) {
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long elapsedTime = System.currentTimeMillis() - startTime;
        String str = String.format("Time elapsed to process request of %s was %d ms", request.getRequestURI(), elapsedTime);

        log.info(str);
    }

    private void addTokenToRestResponse(Object object, int value) {
        if (!(object instanceof RestResponse)) return;

        RestResponse restResponse = (RestResponse) object;
        restResponse.status.credit_count = value;
    }

    private Method getMethodName(Object handler) {
        Method method = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            method = handlerMethod.getMethod();
        }
        return method;
    }

}
