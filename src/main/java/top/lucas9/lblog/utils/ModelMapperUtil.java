package top.lucas9.lblog.utils;

import top.lucas9.lblog.search.model.ArticleDocument;
import top.lucas9.lblog.vo.ArticleVo;

/**
 * @author lucas
 */
public class ModelMapperUtil {
    public static ArticleDocument articleVoToArticleDocument(ArticleVo vo) {
        ArticleDocument articleDocument = new ArticleDocument();
        articleDocument.setId(vo.getId());
        articleDocument.setTitle(vo.getTitle());
        articleDocument.setAuthorId(vo.getAuthorId());
        articleDocument.setAuthorName(vo.getAuthorName());
        articleDocument.setAuthorAvatar(vo.getAuthorAvatar());
        articleDocument.setCategoryId(vo.getCategoryId());
        articleDocument.setLevel(vo.getLevel());
        articleDocument.setRecommend(vo.getRecommend());
        articleDocument.setCommentCount(vo.getCommentCount());
        articleDocument.setViewCount(vo.getViewCount());
        articleDocument.setCreated(vo.getCreated());
        return articleDocument;
    }
}
