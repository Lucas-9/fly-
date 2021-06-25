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
@ApiModel(value="Comment对象", description="")
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "回复的评论id")
    private Long parentId;

    @ApiModelProperty(value = "评论的文章id")
    private Long articleId;

    @ApiModelProperty(value = "评论的用户id")
    private Long userId;

    @ApiModelProperty(value = "“顶”的数量")
    private Integer voteUp;

    @ApiModelProperty(value = "“踩”的数量")
    private Integer voteDown;

    @ApiModelProperty(value = "置顶等级")
    private Integer level;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(Integer voteUp) {
        this.voteUp = voteUp;
    }
    public Integer getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(Integer voteDown) {
        this.voteDown = voteDown;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "content=" + content +
            ", parentId=" + parentId +
            ", articleId=" + articleId +
            ", userId=" + userId +
            ", voteUp=" + voteUp +
            ", voteDown=" + voteDown +
            ", level=" + level +
        "}";
    }
}
