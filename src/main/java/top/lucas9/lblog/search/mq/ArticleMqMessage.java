package top.lucas9.lblog.search.mq;


import java.io.Serializable;

/**
 * @author lucas
 */

public class ArticleMqMessage implements Serializable {
    private Long articleId;
    private String type;

    public ArticleMqMessage() {
    }

    public ArticleMqMessage(Long articleId, String type) {
        this.articleId = articleId;
        this.type = type;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ArticleMqMessage{" +
                "articleId=" + articleId +
                ", type='" + type + '\'' +
                '}';
    }
}
