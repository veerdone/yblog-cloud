package com.github.veerdone.yblog.cloud.common.autoconfig;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.veerdone.yblog.cloud.common.aop.PageAspect;
import com.github.veerdone.yblog.cloud.common.util.SnowflakeIdUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DataBaseAutoConfig {

    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new SnowflakeIdUtil();
    }

    @Bean
    public PageAspect pageAspect() {
        return new PageAspect();
    }
}
