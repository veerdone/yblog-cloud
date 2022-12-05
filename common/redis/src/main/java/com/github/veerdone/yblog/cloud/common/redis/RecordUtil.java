package com.github.veerdone.yblog.cloud.common.redis;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;

public class RecordUtil {
    public static <S, V> ObjectRecord<S, V> objectRecord(S streamKey, V object) {
        return StreamRecords.newRecord().ofObject(object).withStreamKey(streamKey);
    }
}
