package com.github.veerdone.yblog.cloud.common.aop;

import com.github.pagehelper.PageHelper;
import com.github.veerdone.yblog.cloud.common.util.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class PageAspect {
    @Pointcut("@annotation(com.github.veerdone.yblog.cloud.common.aop.Page)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object pageAspect(ProceedingJoinPoint point) throws Throwable {
        int[] param = getParam();
        PageHelper.startPage(param[0], param[1]);
        Object proceed;
        try {
            proceed = point.proceed();
        } finally {
            PageHelper.clearPage();
        }
        return proceed;
    }

    private int[] getParam() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        Assert.notNull(requestAttributes, "ServletRequestAttributes is null");
        HttpServletRequest request = requestAttributes.getRequest();

        return RequestUtil.getPageParam(request);
    }

}
