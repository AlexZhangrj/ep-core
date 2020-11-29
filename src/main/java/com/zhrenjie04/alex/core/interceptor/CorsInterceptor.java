package com.zhrenjie04.alex.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 张人杰
 */
@Component
public class CorsInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "true");
//        response.setHeader("Access-Control-Allow-Methods", request.getMethod() + "," + request.getHeader("Access-Control-Request-Method"));
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "-1");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, authorization, Accept, Origin, XRequestedWith, Content-Type, LastModified");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization, authorization,content-type");
        response.setHeader("Access-Control-Allow-Credentials","true");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
