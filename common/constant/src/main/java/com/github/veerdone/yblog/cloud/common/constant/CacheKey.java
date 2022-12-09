package com.github.veerdone.yblog.cloud.common.constant;

public class CacheKey {
    /**
     * 用户注册key, + 邮箱
     */
    public static final String USER_REGISTER_CAPTCHA = "user:register:captcha:";

    /**
     * 用户信息缓存key, + user_id
     */
    public static final String USER_INFO_QUERY_BY_ID = "user:info:query:by:id:";

    /**
     * 文章分类缓存key, + classify_id
     */
    public static final String ARTICLE_CLASSIFY_QUERY_BY_ID = "article:classify:query:by:id:";

    /**
     * 文章标签缓存key, + label_id
     */
    public static final String ARTICLE_LABEL_QUERY_BY_ID = "article:label:query:by:id:";

    /**
     * 文章审核 stream key
     */
    public static final String ARTICLE_REVIEW_STREAM_KEY = "stream:article:review";

    /**
     * 文章审核 stream group
     */
    public static final String ARTICLE_REVIEW_STREAM_GROUP = "article-review-stream-group";

    /**
     * 文章点赞 set key, + 用户id
     */
    public static final String ARTICLE_THUMBS_UP = "article:thumbs:up:";
}
