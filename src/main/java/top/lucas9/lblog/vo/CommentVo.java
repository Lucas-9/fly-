package top.lucas9.lblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.lucas9.lblog.entity.Comment;

import java.io.Serializable;

/**
 * @author lucas
 */
@ApiModel(value="CommentVo对象", description="")
public class CommentVo extends Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布者id")
    private Long authorId;
    @ApiModelProperty(value = "发布者name")
    private String authorName;
    @ApiModelProperty(value = "发布者头像")
    private String authorAvatar;

    public CommentVo() {
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

    @Override
    public String toString() {
        return "CommentVo{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                '}';
    }
}
