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


    public static final String ARTICLE_LABEL_HASH = "article:label:hash";

    /**
     * 文章信息缓存key, key + article_id
     */
    public static final String ARTICLE_INFO_QUERY_BY_ID = "article:info:query:by:id:";

    /**
     * 用户文章点赞 Set key, key + user_id
     */
    public static final String USER_ARTICLE_THUMBS_UP = "user:article:thumbsUp:";

    /**
     * 用户文章收藏 Set key, key + user_id
     */
    public static final String USER_ARTICLE_COLLECTION = "user:article:collection:";

    /**
     * 文章内容缓存key, key + article_id
     */
    public static final String ARTICLE_CONTENT_QUERY_BY_ID = "article:content:query:by:id:";
}
