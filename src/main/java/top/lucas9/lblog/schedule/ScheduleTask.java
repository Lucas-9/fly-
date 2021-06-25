package top.lucas9.lblog.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.service.ArticleService;
import top.lucas9.lblog.utils.RedisUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lucas
 */
@Component
public class ScheduleTask {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;
    /**
     * 每 10 秒钟执行一次，刷新访问量到 mysql
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void refreshViewCount() {
        Set<String> keys = redisUtil.keys(Constant.ARTICLE_SEARCH_KEY);

        List<String> ids = new ArrayList<>();
        for (String key : keys) {
            if(redisUtil.hashHasKey(key, "viewCount")){
                ids.add(key.substring(Constant.ARTICLE_PREFIX.length()));
            }
        }
        if (ids.isEmpty()) {
            return;
        }
        List<Article> articles = articleService.list(new QueryWrapper<Article>().in("id", ids));
        if (articles.isEmpty()) {
            return;
        }

        for (Article article : articles) {
            Integer viewCount = (Integer) redisUtil.hashGet(Constant.ARTICLE_PREFIX + article.getId(), "viewCount");
            article.setViewCount(viewCount);
        }
        boolean isSuccess = articleService.updateBatchById(articles);
        if(isSuccess) {
            for (String id : ids) {
                redisUtil.hashDel(Constant.ARTICLE_PREFIX + id, "viewCount");
            }
        }
    }

    /**
     * 每五天执行一次， 重置推荐等级
     * 0 0 0 1/1 * ?
     */
    @Scheduled(cron = "0 0 0 1/5 * ?")
    public void updateLevel() {
        List<Article> articles = articleService.list(new QueryWrapper<Article>()
                .gt("level", 0)
                .orderByAsc("level"));
        Long level = 1L;
        for (Article article : articles) {
            article.setLevel(level);
            level++;
        }
        articleService.updateBatchById(articles);
    }

    /**
     * 每天一点从redis中过期前一天的热门评论文章
     */
    @Scheduled(cron = "0 0 1 ? * * ")
    public void expiredArticlesOfToday() {
        redisUtil.removeKey(LocalDate.now().minus( 1, ChronoUnit.DAYS));
    }
}
