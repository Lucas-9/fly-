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
@ApiModel(value="Category对象", description="")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String name;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "概括")
    private String summary;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "该分类的文章数量")
    private Integer articleCount;

    @ApiModelProperty(value = "排序编码")
    private Integer orderNum;

    @ApiModelProperty(value = "父级分类的id")
    private Long parentId;

    @ApiModelProperty(value = "seo关键字")
    private String metaKeywords;

    @ApiModelProperty(value = "seo描述内容")
    private String metaDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }
    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    @Override
    public String toString() {
        return "Category{" +
            "name=" + name +
            ", content=" + content +
            ", summary=" + summary +
            ", icon=" + icon +
            ", articleCount=" + articleCount +
            ", orderNum=" + orderNum +
            ", parentId=" + parentId +
            ", metaKeywords=" + metaKeywords +
            ", metaDescription=" + metaDescription +
        "}";
    }
}
