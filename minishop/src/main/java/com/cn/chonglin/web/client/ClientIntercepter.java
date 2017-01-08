package com.cn.chonglin.web.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端拦截器
 *
 * @author wu
 */
public class ClientIntercepter extends HandlerInterceptorAdapter {
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(modelAndView != null){
            ModelMap modelMap = modelAndView.getModelMap();
            if(!ANONYMOUS_USER.equals(username)){
                modelMap.addAttribute("isAuth", true);
            }
        }

    }
}
