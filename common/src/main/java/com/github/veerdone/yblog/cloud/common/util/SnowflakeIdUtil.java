package com.github.veerdone.yblog.cloud.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

public class SnowflakeIdUtil implements IdentifierGenerator {
    private final Snowflake snowflake = IdUtil.getSnowflake();

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }
}
