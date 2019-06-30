package com.wfq.manager.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 19:04
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(public * com.wfq.manager.api.*.*.*(..))")
    private void controllerAspect() {
    }

    @Around(value = "controllerAspect()")
    public Object methodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        long beginTime = System.currentTimeMillis();
        // 打印请求内容
        log.info(">>>>>>>>>>>>>请求内容 start >>>>>>>>>>>>>>");
        log.info("请求IP:" + request.getRemoteAddr());
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求方式:" + request.getMethod());
        log.info("请求类方法:" + joinPoint.getSignature());
        log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        log.info(">>>>>>>>>>>>>>请求内容 end >>>>>>>>>>>>>>>>");
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("<<<<<<<<<<<<返回结果 start <<<<<<<<<<<<<<:");
        log.info("耗时：" + (endTime - beginTime));
        log.info("返回内容：" + result);
        log.info("<<<<<<<<<<<<返回结果 end <<<<<<<<<<<<<<:");
        return result;
    }

}
