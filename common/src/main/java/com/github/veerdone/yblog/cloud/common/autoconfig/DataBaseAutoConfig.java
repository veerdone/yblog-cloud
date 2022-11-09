package com.github.veerdone.yblog.cloud.common.autoconfig;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.github.veerdone.yblog.cloud.common.aop.PageAspect;
import com.github.veerdone.yblog.cloud.common.util.SnowflakeIdUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DataBaseAutoConfig {

    @Bean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
        return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new SnowflakeIdUtil());
    }

    @Bean
    public PageAspect pageAspect() {
        return new PageAspect();
    }
}
