package com.github.veerdone.yblog.cloud.common.page;

import com.github.pagehelper.PageInfo;
import com.github.veerdone.yblog.cloud.common.response.result.ListResult;

import java.util.List;

public class PageUtil {
    public static <T> ListResult<T> response(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        return ListResult.result(list, total);
    }
}
