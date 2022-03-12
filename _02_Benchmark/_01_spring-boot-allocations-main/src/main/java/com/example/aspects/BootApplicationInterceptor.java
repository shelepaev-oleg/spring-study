package com.example.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.util.ClassUtils;

@Aspect
public class BootApplicationInterceptor {

	private static Log logger = LogFactory.getLog(BootApplicationInterceptor.class);

    @Around("execution(* org.springframework.boot.system.ApplicationHome.findSource(..))")
    public Object source(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

	@Around("execution(* org.springframework.boot.SpringApplication.isWebApplicationContext(..))")
	public Object webApplication(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!ClassUtils.isPresent("org.springframework.web.context.WebApplicationContext",
				null)) {
			return false;
		}
		return proceed(joinPoint);
	}

	private Object proceed(ProceedingJoinPoint joinPoint) {
		try {
			Object result = joinPoint.proceed();
			if (logger.isDebugEnabled()) {
				logger.debug(joinPoint.toShortString() + ": " + result);
			}
			return result;
		}
		catch (Throwable t) {
			if (logger.isDebugEnabled()) {
				logger.debug(joinPoint.toShortString() + ": " + t);
			}
			return null;
		}
	}

}
