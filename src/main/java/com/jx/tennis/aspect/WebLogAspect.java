/**
 * Created on 2018年3月23日 下午5:16:54
 */
package com.jx.tennis.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


/**
 * Web层日志切面 . <br>
 * 
 * @author hkb <br>
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    /**
     * 日志对象
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 开始时间
     */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<Long>();

    /**
     * 设置开始时间
     * 
     * @param start
     */
    public static void set(Long start) {
        START_TIME.set(start);
    }

    /**
     * 获取开始时间
     * 
     * @return
     */
    public static Long get() {
        return START_TIME.get();
    }

    /**
     * 删除开始时间
     */
    public static void remove() {
        START_TIME.remove();
    }

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.curriculum.controller..*.*(..))")
    public void webLog() {

    }

    /**
     * 在切入点开始处切入内容
     * 
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        START_TIME.set(System.currentTimeMillis());

        log.info("类方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());        
    }

    /**
     * 在切入点return内容之后切入内容(可以用来对处理返回值做一些加工处理)
     * 
     * @param obj
     * @throws Throwable
     */
    @AfterReturning(returning = "obj", pointcut = "webLog()")
    public void doAfterReturning(Object obj) throws Throwable {
        // 处理完请求,返回内容
        log.info("返回结果 : " + JSON.toJSONString(obj));
        log.info("耗时(豪秒) : " + (System.currentTimeMillis() - START_TIME.get()));
    }

}
