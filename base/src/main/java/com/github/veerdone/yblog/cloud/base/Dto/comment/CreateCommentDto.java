package com.github.veerdone.yblog.cloud.base.Dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateCommentDto {
    @NotNull(message = "item_id 不能为空")
    private Long itemId;

    @NotNull(message = "user_id 不能为空")
    private Long userId;

    @NotBlank(message = "comment_content 不能未空")
    @Size(min = 1, max = 300, message = "comment_content 内容最长为300个字符")
    private String commentContent;

    @NotNull(message = "type 不能为空")
    private Integer type;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateCommentDto that = (CreateCommentDto) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(userId, that.userId) && Objects.equals(commentContent, that.commentContent) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, userId, commentContent, type);
    }

    @Override
    public String toString() {
        return "CreateCommentDto{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", commentContent='" + commentContent + '\'' +
                ", type=" + type +
                '}';
    }
}
