package com.github.veerdone.yblog.cloud.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CustomMybatisAutoConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new TimestampMetaObjectHandler();
    }
}
