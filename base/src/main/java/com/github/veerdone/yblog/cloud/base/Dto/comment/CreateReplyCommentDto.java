package com.github.veerdone.yblog.cloud.base.Dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateReplyCommentDto {
    @NotNull(message = "item_id 不能为空")
    private Long itemId;

    @NotNull(message = "comment_id 不能为空")
    private Long commentId;

    @NotNull(message = "reply_user_id 不能为空")
    private Long replyUserId;

    @NotBlank(message = "reply_comment_content 不能为空")
    @Size(min = 1, max = 300, message = "reply_comment_content 最长为300个字符")
    private String replyCommentContent;

    @NotNull(message = "reply_comment_id 不能为空")
    private Long replyCommentId;

    @NotNull(message = "reply_user_id 不能为空")
    private Long replyToUserId;

    @NotNull(message = "type 不能为空")
    private Integer type;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyCommentContent() {
        return replyCommentContent;
    }

    public void setReplyCommentContent(String replyCommentContent) {
        this.replyCommentContent = replyCommentContent;
    }

    public Long getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(Long replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Long getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Long replyToUserId) {
        this.replyToUserId = replyToUserId;
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
        CreateReplyCommentDto that = (CreateReplyCommentDto) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(commentId, that.commentId) && Objects.equals(replyUserId, that.replyUserId) && Objects.equals(replyCommentContent, that.replyCommentContent) && Objects.equals(replyCommentId, that.replyCommentId) && Objects.equals(replyToUserId, that.replyToUserId) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, commentId, replyUserId, replyCommentContent, replyCommentId, replyToUserId, type);
    }

    @Override
    public String toString() {
        return "CreateReplyCommentDto{" +
                "itemId=" + itemId +
                ", commentId=" + commentId +
                ", replyUserId=" + replyUserId +
                ", replyCommentContent='" + replyCommentContent + '\'' +
                ", replyCommentId=" + replyCommentId +
                ", replyToUserId=" + replyToUserId +
                ", type=" + type +
                '}';
    }
}
