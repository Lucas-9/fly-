package top.lucas9.lblog.entity;

import top.lucas9.lblog.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@ApiModel(value="UserMessage对象", description="")
public class UserMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送消息的用户id")
    private Long userIdOfSend;

    @ApiModelProperty(value = "接收消息的用户id")
    private Long userIdOfReceive;

    @ApiModelProperty(value = "消息可能关联的帖子")
    private Long articleId;

    @ApiModelProperty(value = "消息可能关联的评论")
    private Long commentId;
    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "消息类型， 0是系统消息，1是评论文章，2是回复评论")
    private Integer type;

    public Long getUserIdOfSend() {
        return userIdOfSend;
    }

    public void setUserIdOfSend(Long userIdOfSend) {
        this.userIdOfSend = userIdOfSend;
    }
    public Long getUserIdOfReceive() {
        return userIdOfReceive;
    }

    public void setUserIdOfReceive(Long userIdOfReceive) {
        this.userIdOfReceive = userIdOfReceive;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
            "userIdOfSend=" + userIdOfSend +
            ", userIdOfReceive=" + userIdOfReceive +
            ", articleId=" + articleId +
            ", commentId=" + commentId +
            ", content=" + content +
            ", type=" + type +
        "}";
    }
}
