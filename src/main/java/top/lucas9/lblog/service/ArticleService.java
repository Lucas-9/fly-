package top.lucas9.lblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.lucas9.lblog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lucas9.lblog.vo.ArticleVo;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface ArticleService extends IService<Article> {
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    ArticleVo selectOneArticle(QueryWrapper<Article> wrapper);

    void updateViewCount(ArticleVo articleVo);

    List selectHotCommentArticlesOfToDay();

    void plusCommentCount(LocalDate localDate , Long articleId, Boolean isPlus);

    Long selectMaxLevel();
}
