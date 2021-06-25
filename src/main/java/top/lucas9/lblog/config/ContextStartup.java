package top.lucas9.lblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.entity.Category;
import top.lucas9.lblog.service.ArticleService;
import top.lucas9.lblog.service.CategoryService;
import top.lucas9.lblog.utils.RedisUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目启动加载信息到 redis
 * @author lucas
 */
@Configuration
public class ContextStartup implements CommandLineRunner {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        initCategoriesCache();
        initHotCommentArticlesCache();
    }

    public void initCategoriesCache() {
        List<Category> mysqlCategories = categoryService.list(new QueryWrapper<Category>().eq("status", 1));
        // 存入redis
        List<Category> redisCategories = redisUtil.listRange(Constant.CATEGORIES_KEY, 0L, -1L);
        for (Category mysqlCategory : mysqlCategories) {
            boolean flag = true;
            for (Category redisCategory : redisCategories) {
                if (redisCategory.getId().equals(mysqlCategory.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                redisUtil.listRightPush(Constant.CATEGORIES_KEY, mysqlCategory);
            }
        }
    }

    public void initHotCommentArticlesCache() {
        LocalDate today = LocalDate.now();
        List<Article> list = articleService.list(new QueryWrapper<Article>()
                .ge("created", today)
                .gt("comment_count", 0)
                .select("id", "title", "comment_count", "view_count"));
        for (Article article : list) {
            String key = Constant.ARTICLE_PREFIX + article.getId();
            redisUtil.zSetAdd(today, article.getId(), article.getCommentCount());
            redisUtil.hashPut(key, "title", article.getTitle());
            redisUtil.hashPut(key, "id", article.getId());
            redisUtil.hashPut(key, "commentCount", article.getCommentCount());
        }
    }
}
