package com.github.veerdone.yblog.cloud.common.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

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
}
