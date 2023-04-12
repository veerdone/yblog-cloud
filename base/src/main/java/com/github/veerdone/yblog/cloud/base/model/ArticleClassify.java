package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_article_classify")
public class ArticleClassify implements Serializable {
    private static final long serialVersionUID = 4345353546545L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String classifyName;

    private String url;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleClassify that = (ArticleClassify) o;
        return Objects.equals(id, that.id) && Objects.equals(classifyName, that.classifyName) && Objects.equals(url, that.url) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classifyName, url, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ArticleClassify{" +
                "id=" + id +
                ", classifyName='" + classifyName + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
