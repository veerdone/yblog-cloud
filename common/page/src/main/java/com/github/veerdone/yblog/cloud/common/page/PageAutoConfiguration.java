package com.github.veerdone.yblog.cloud.common.page;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class PageAutoConfiguration {
    @Bean
    public PageAspect pageAspect() {
        return new PageAspect();
    }

    @Bean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
        return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new SnowflakeIdUtil());
    }

    public static class SnowflakeIdUtil implements IdentifierGenerator {
        private final Snowflake snowflake = IdUtil.getSnowflake();

        @Override
        public Number nextId(Object entity) {
            return snowflake.nextId();
        }
    }
}
