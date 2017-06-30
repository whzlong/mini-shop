package com.cn.chonglin.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * 记录请求URL以及参数
 *
 * @author wu
 */
@Aspect
@Component
public class ApiLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.cn.chonglin.web..*.*(..))")
    public void apiLog(){}

    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        javax.servlet.http.HttpServletRequest request = attributes.getRequest();

        logger.info("Request url: {}", request.getRequestURL().toString());
        logger.info("Request method: {}", request.getMethod());
        logger.info("Request ip: {}", request.getRemoteAddr());
        logger.info("Invoked method: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("Request parameters: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "apiLog()")
    public void doAfterReturning(Object ret) throws Throwable{
        logger.info("Response: {}", ret);

        logger.info("Response time: {}", System.currentTimeMillis() - startTime.get());
    }
}
