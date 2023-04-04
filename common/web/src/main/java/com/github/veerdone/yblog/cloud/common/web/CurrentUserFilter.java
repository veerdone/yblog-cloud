package com.github.veerdone.yblog.cloud.common.web;

import com.github.veerdone.yblog.cloud.common.util.RequestUtil;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CurrentUserFilter extends OncePerRequestFilter implements Ordered {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestUtil.setCurrentUser(request);
        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestUtil.clear();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
