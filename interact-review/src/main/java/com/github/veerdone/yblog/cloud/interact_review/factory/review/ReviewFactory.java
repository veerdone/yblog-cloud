package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import java.util.HashMap;
import java.util.Map;

public class ReviewFactory {
    private final static Map<String, ReviewHandler> reviewMap = new HashMap<>();

    public static void addReview(String type, ReviewHandler reviewHandler) {
        reviewMap.put(type, reviewHandler);
    }

    public static ReviewHandler getReview(String type) {
        return reviewMap.get(type);
    }
}
