package top.lucas9.lblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.entity.Comment;
import top.lucas9.lblog.entity.User;
import top.lucas9.lblog.entity.UserMessage;
import top.lucas9.lblog.mapper.ArticleMapper;
import top.lucas9.lblog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lucas9.lblog.utils.RedisUtil;
import top.lucas9.lblog.vo.ArticleVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public IPage<ArticleVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if (null == level) {
            level = -1;
        }
        QueryWrapper<Article> detailQueryWrapper = new QueryWrapper<Article>()
                .eq(null != categoryId, "category_id", categoryId)
                .eq(null != userId, "user_id", userId)
                .eq(0 == level, "level", 0)
                .gt(0 < level, "level", 0)
                .orderByDesc(null != order ,order);
        return articleMapper.selectArticles(page, detailQueryWrapper);
    }

    @Override
    public ArticleVo selectOneArticle(QueryWrapper<Article> wrapper) {
        return articleMapper.selectOneArticle(wrapper);
    }

    @Override
    public void updateViewCount(ArticleVo articleVo) {
        Integer viewCount = (Integer) redisUtil.hashGet(Constant.ARTICLE_PREFIX + articleVo.getId(), "viewCount");
        if(viewCount != null) {
            articleVo.setViewCount(viewCount + 1);
        } else {
            articleVo.setViewCount(articleVo.getViewCount() + 1);
        }
        redisUtil.hashPut(Constant.ARTICLE_PREFIX + articleVo.getId(), "viewCount", articleVo.getViewCount());
    }

    @Override
    public List<ArticleVo> selectHotCommentArticlesOfToDay() {
        Set<Long> zSetRank = redisUtil.getZSetRank(LocalDate.now(), 0, 5);
        List<ArticleVo> hotArticle = new ArrayList<>();
        for (Long key : zSetRank) {
            ArticleVo articleVo = new ArticleVo();
            String articleKey = Constant.ARTICLE_PREFIX + key;
            articleVo.setId(key);
            String title = (String)redisUtil.hashGet(articleKey, "title");
            articleVo.setTitle(title);
            articleVo.setCommentCount((Integer) redisUtil.hashGet(articleKey, "commentCount"));
            hotArticle.add(articleVo);
        }
        return hotArticle;
    }

    @Override
    public void plusCommentCount(LocalDate localDate, Long articleId, Boolean isPlus) {
        Double newScore = redisUtil.zSetPlusScore(localDate, articleId, isPlus ? 1 : -1);
        String key = Constant.ARTICLE_PREFIX + articleId;
        redisUtil.hashPut(key, "commentCount", newScore.intValue());
    }

    @Override
    public Long selectMaxLevel() {
        return articleMapper.selectMaxLevel();
    }
}
