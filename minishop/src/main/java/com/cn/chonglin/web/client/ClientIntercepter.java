package com.cn.chonglin.web.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        System.out.println(request.getContentType());

        if(modelAndView != null){
            ModelMap modelMap = modelAndView.getModelMap();
            if(!ANONYMOUS_USER.equals(authentication.getName())){
                modelMap.addAttribute("isAuth", true);

                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    modelMap.addAttribute("role", grantedAuthority.getAuthority());
                }
            }else{
//                if(MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())){
//                    PrintWriter out = null;
//
//                    try{
//                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//                        out = response.getWriter();
//                        out.write(ResponseResult.error(10,"Auth").toString());
//                    }catch (IOException ex){
//                        ex.printStackTrace();
//                    }finally{
//                        out.flush();
//                        out.close();
//                    }
//
//                }
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //super.afterCompletion(request, response, handler, ex);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        System.out.println(request.getContentType());
//
//        if(ANONYMOUS_USER.equals(authentication.getName())){
//
//            if(MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())){
//                PrintWriter out = null;
//
//                try{
//                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//                    out = response.getWriter();
//                    out.write(ResponseResult.error(10,"Auth").toString());
//                }catch (IOException io){
//                    io.printStackTrace();
//                }finally{
//                    out.flush();
//                    out.close();
//                }
//
//            }
//        }
    }
}
