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
@ApiModel(value="UserAction对象", description="")
public class UserAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "动作类型")
    private String action;

    @ApiModelProperty(value = "得分")
    private Integer point;

    @ApiModelProperty(value = "关联的帖子id")
    private String articleId;

    @ApiModelProperty(value = "关联的评论id")
    private String commentId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "UserAction{" +
            "userId=" + userId +
            ", action=" + action +
            ", point=" + point +
            ", articleId=" + articleId +
            ", commentId=" + commentId +
        "}";
    }
}
