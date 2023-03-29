package com.github.veerdone.yblog.cloud.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferUtil {
    private static final Logger log = LoggerFactory.getLogger(ByteBufferUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    public static <T> T readValue(ByteBuffer buffer, Class<T> c) throws IOException {
        int limit = buffer.limit();
        if (limit == 0) {
            return null;
        }

        byte[] bytes = new byte[limit];
        buffer.get(bytes);

        try {
            return objectMapper.readValue(bytes, c);
        } catch (IOException e) {
            log.warn("read bytes value to {} failed, reason: {}", c.getName(), e);
            throw e;
        }
    }
}
