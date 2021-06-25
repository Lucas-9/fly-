package top.lucas9.lblog.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@ApiModel(value="Article对象", description="")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "编辑模式：html可视化，markdown ..")
    private String editMode;

    @ApiModelProperty(value = "分类id")
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "支持人数")
    private Integer voteUp;

    @ApiModelProperty(value = "反对人数")
    private Integer voteDown;

    @ApiModelProperty(value = "访问量")
    private Integer viewCount;

    @ApiModelProperty(value = "评论数量")
    private Integer commentCount;

    @ApiModelProperty(value = "是否为精华")
    private Boolean recommend;

    @ApiModelProperty(value = "置顶等级")
    private Long level;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getEditMode() {
        return editMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Article{" +
            "title=" + title +
            ", content=" + content +
            ", editMode=" + editMode +
            ", categoryId=" + categoryId +
            ", userId=" + userId +
            ", voteUp=" + voteUp +
            ", voteDown=" + voteDown +
            ", viewCount=" + viewCount +
            ", commentCount=" + commentCount +
            ", recommend=" + recommend +
            ", level=" + level +
        "}";
    }
}
