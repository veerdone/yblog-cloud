package com.github.veerdone.yblog.cloud.common.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RequestUtil {
    private static final ThreadLocal<CurrentUser> CURRENT_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static int[] getPageParam(HttpServletRequest request) {
        int defaultCurrent = 1;
        int defaultPageSize = 10;

        String current = request.getParameter("current");
        String pageSize = request.getParameter("page_size");
        if (StrUtil.isEmpty(current) || StrUtil.isEmpty(pageSize)) {
            return new int[]{defaultCurrent, defaultPageSize};
        }

        try {
            defaultCurrent = Integer.parseInt(current);
            defaultPageSize = Integer.parseInt(pageSize);
        } catch (Exception ignored) {

        }

        return new int[]{defaultCurrent, defaultPageSize};
    }

    public static void setCurrentUser(HttpServletRequest request) {
        String userId = request.getParameter("user_id");
        String role = request.getParameter("role");
        if (Objects.nonNull(userId) && Objects.nonNull(role)) {
            CurrentUser currentUser = new CurrentUser();
            currentUser.setRole(Integer.valueOf(role));
            currentUser.setUserId(Long.valueOf(userId));
            CURRENT_USER_THREAD_LOCAL.set(currentUser);
        }
    }

    public static void clear() {
        CURRENT_USER_THREAD_LOCAL.remove();
    }

    public static Long getUserId() {
        CurrentUser currentUser = CURRENT_USER_THREAD_LOCAL.get();
        if (Objects.nonNull(currentUser)) {
            return currentUser.getUserId();
        }

        return null;
    }

    public static Integer getRole() {
        CurrentUser currentUser = CURRENT_USER_THREAD_LOCAL.get();
        if (Objects.nonNull(currentUser)) {
            return currentUser.getRole();
        }

        return null;
    }

    static class CurrentUser {
        private Long userId;

        private Integer role;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CurrentUser that = (CurrentUser) o;
            return Objects.equals(userId, that.userId) && Objects.equals(role, that.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, role);
        }
    }
}
