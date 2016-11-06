package com.happydriving.rockets.server.common;

import com.happydriving.rockets.server.utils.CommonConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazhiqiang
 */
public class FakeCoachLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute(CommonConstants.SESSION_USER_ID) == null
                || !CommonConstants.ROLE_COACH.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
            request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, 179);
            request.getSession().setAttribute(CommonConstants.SESSION_ROLE, CommonConstants.ROLE_COACH);
        }
        return true;
    }
}
