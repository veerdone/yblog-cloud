package com.github.veerdone.yblog.cloud.interact_review;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.veerdone.yblog.cloud.interact_review.mapper")
public class InteractReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(InteractReviewApplication.class);
    }
}
