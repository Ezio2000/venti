package org.venti.guarantee.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(1)
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Pointcut("execution(* org.venti.guarantee.controller..*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        // 构建请求日志
        Map<String, Object> requestLog = new HashMap<>();
        requestLog.put("请求URL", request.getRequestURL().toString());
        requestLog.put("HTTP方法", request.getMethod());
        requestLog.put("IP地址", request.getRemoteAddr());
        requestLog.put("类方法", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        requestLog.put("请求参数", getRequestParams(request));
        requestLog.put("方法参数", joinPoint.getArgs());

        try {
            log.info("请求开始 ===> {}", objectMapper.writeValueAsString(requestLog));
        } catch (Exception e) {
            log.error("记录请求日志出错", e);
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        // 构建响应日志
        Map<String, Object> responseLog = new HashMap<>();
        responseLog.put("请求URL", request.getRequestURL().toString());
        responseLog.put("HTTP方法", request.getMethod());
        responseLog.put("类方法", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // 只记录结果的基本信息，避免日志过大
        if (result != null) {
            responseLog.put("响应类型", result.getClass().getSimpleName());
            responseLog.put("响应数据", result.toString());
        } else {
            responseLog.put("响应数据", "null");
        }

        try {
            log.info("请求结束 <=== {}", objectMapper.writeValueAsString(responseLog));
        } catch (Exception e) {
            log.error("记录响应日志出错", e);
        }
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        log.error("方法执行异常: {}.{}, 异常信息: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getMessage(),
                e);
    }

    /**
     * 获取请求参数
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        return params;
    }

}