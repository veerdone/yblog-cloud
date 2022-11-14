package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_article_label")
public class ArticleLabel implements Serializable {
    public static final Long serialVersionUID = 545454545424234L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotEmpty(message = "标签名称不能为空")
    @Length(max = 20, message = "标签名称最长为20个字符")
    private String labelName;

    @NotNull(message = "分类id不能为空")
    @Min(value = 1, message = "分类id不能小于1")
    private Long classifyId;

    @NotEmpty(message = "url不能为空")
    @Length(max = 20, message = "url最长为20个字符")
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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
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
        ArticleLabel that = (ArticleLabel) o;
        return Objects.equals(id, that.id) && Objects.equals(labelName, that.labelName) && Objects.equals(classifyId, that.classifyId) && Objects.equals(url, that.url) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labelName, classifyId, url, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ArticleLabel{" +
                "id=" + id +
                ", labelName='" + labelName + '\'' +
                ", classifyId=" + classifyId +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
