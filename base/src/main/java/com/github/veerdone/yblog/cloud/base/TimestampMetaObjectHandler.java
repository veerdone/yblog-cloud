package com.github.veerdone.yblog.cloud.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.function.Supplier;

public class TimestampMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        long currentTimeMillis = System.currentTimeMillis();
        this.strictInsertFill(metaObject, "createTime", () -> currentTimeMillis, Long.class);
        this.strictInsertFill(metaObject, "updateTime", () -> currentTimeMillis, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", System::currentTimeMillis, Long.class);
    }
}
