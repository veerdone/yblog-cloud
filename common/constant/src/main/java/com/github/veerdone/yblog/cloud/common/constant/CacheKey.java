package com.github.veerdone.yblog.cloud.common.constant;

public class CacheKey {
    /**
     * 用户注册key, key + 邮箱
     */
    public static final String USER_REGISTER_CAPTCHA = "user:register:captcha:";

    /**
     * 用户登陆key, key + 邮箱
     */
    public static final String USER_LOGIN_CAPTCHA = "user:login:captcha:";

    /**
     * 用户信息缓存key, key + user_id
     */
    public static final String USER_INFO_QUERY_BY_ID = "user:info:query:by:id:";

    /**
     * 文章分类缓存key, key + classify_id
     */
    public static final String ARTICLE_CLASSIFY_QUERY_BY_ID = "article:classify:query:by:id:";

    /**
     * 文章标签缓存key, key + label_id
     */
    public static final String ARTICLE_LABEL_QUERY_BY_ID = "article:label:query:by:id:";

    /**
     * 文章点赞 set key, key + 用户id
     */
    public static final String ARTICLE_THUMBS_UP = "article:thumbs:up:";

    public static final String ARTICLE_CONTENT_QUERY_BY_ID = "article:content:query:by:id:";
}
