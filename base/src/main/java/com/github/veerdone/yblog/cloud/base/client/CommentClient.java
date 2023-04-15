package com.github.veerdone.yblog.cloud.base.client;

import java.util.List;
import java.util.Map;

public interface CommentClient {
    void updateStatus(Long id, Integer commentType, Integer status, Integer extra);

    List<String> listCommentContentByIdExtraMap(Map<Long, Integer> idExtraMap);
}
