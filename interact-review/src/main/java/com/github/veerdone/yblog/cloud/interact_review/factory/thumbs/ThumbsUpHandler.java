package com.github.veerdone.yblog.cloud.interact_review.factory.thumbs;

import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import org.springframework.beans.factory.InitializingBean;

public interface ThumbsUpHandler extends InitializingBean {
    void save(ThumbsUp thumbsUp);

    void cancel(ThumbsUp thumbsUp);
}
