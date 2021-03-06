package com.happydriving.rockets.server.common;

import com.happydriving.rockets.server.utils.CommonConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author mazhiqiang
 */
public class CoachLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String servletPath = request.getServletPath();
        if (!servletPath.startsWith("/coach")) {
            return true;
        }
        if (request.getSession().getAttribute(CommonConstants.SESSION_USER_ID) != null
                && CommonConstants.ROLE_COACH.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/user/login");
        return false;
    }
}
