package top.lucas9.lblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.lucas9.lblog.entity.Article;

import java.io.Serializable;

/**
 * @author lucas
 */
@ApiModel(value="DetailVo对象", description="")
public class ArticleVo extends Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布者id")
    private Long authorId;
    @ApiModelProperty(value = "发布者name")
    private String authorName;
    @ApiModelProperty(value = "发布者头像")
    private String authorAvatar;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    public ArticleVo() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
