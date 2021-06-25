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
 * @since 2021-06-22
 */
@ApiModel(value="UserFavorites对象", description="")
public class UserFavorites extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    private Long articleId;

    private Long articleUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getArticleUserId() {
        return articleUserId;
    }

    public void setArticleUserId(Long articleUserId) {
        this.articleUserId = articleUserId;
    }

    @Override
    public String toString() {
        return "UserFavorites{" +
            "userId=" + userId +
            ", articleId=" + articleId +
            ", articleUserId=" + articleUserId +
        "}";
    }
}
