package com.github.veerdone.yblog.cloud.comment;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.github.veerdone.yblog.cloud.comment.mapper")
public class CommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class);
    }
}
