package com.happydriving.rockets.server.utils;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * Created by jasonzhu on 6/8/15.
 */

@Component
@Aspect
public class LoggingAspect {
    public static final Log LOG = LogFactory.getLog(LoggingAspect.class);


    @Pointcut("execution(com.happydriving.rockets.server.common.json.ResponseJsonObject com.happydriving.rockets.*.controller..*.*(..))" +
            " || execution(com.happydriving.rockets.server.common.json.ResponseJsonObject com.happydriving.rockets.*.*.controller..*.*(..))")
    protected void controllerThrowableCatcher() {
    }


    /**
     * 用于将controller所有返回ResponseJsonObject的方法的异常捕获，后台打印日志，并给前端返回对应信息的AOP
     *
     * @param joinPoint
     * @return
     */
    @Around("controllerThrowableCatcher()")
    public ResponseJsonObject controllerThrowableCatcherAOP(ProceedingJoinPoint joinPoint) {
        try {
            ResponseJsonObject rtn = (ResponseJsonObject) joinPoint.proceed();
            return rtn;
        } catch (Throwable throwable) {
            LOG.error("[" + joinPoint.getSignature().toString() + "]\n" + ExceptionUtils.getStackTrace(throwable));
            return new ResponseJsonObject(false, "internal error: " + throwable.getMessage());
        }
    }

}
