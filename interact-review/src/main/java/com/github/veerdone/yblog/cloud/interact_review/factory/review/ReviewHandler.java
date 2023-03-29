package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import com.github.veerdone.yblog.cloud.base.model.Review;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public interface ReviewHandler extends InitializingBean {
    void review(MessageView messageView) throws IOException;

    void reviewThrough(Review review);

    void reviewFailed(Review review);
}
