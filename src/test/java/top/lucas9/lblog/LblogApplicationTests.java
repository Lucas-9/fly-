package top.lucas9.lblog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.entity.Category;
import top.lucas9.lblog.service.ArticleService;
import top.lucas9.lblog.service.CategoryService;
import top.lucas9.lblog.utils.RedisUtil;
import top.lucas9.lblog.vo.ArticleVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
class LblogApplicationTests {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        redisUtil.zSetPlusScore(LocalDate.now(), 2, 1);
        Set<Long> zSetRank = redisUtil.getZSetRank(LocalDate.now(), 0, 5);
        for (Long key : zSetRank) {
            ArticleVo articleVo = new ArticleVo();
            String articleKey = Constant.ARTICLE_PREFIX + key;
            articleVo.setId(key);
            String title = (String)redisUtil.hashGet(articleKey, "title");
            articleVo.setTitle(title);
            articleVo.setCommentCount((Integer) redisUtil.hashGet(articleKey, "commentCount"));
            System.out.println(articleVo);
        }
    }

    @Test
    public void testInitCategory() {
        List<Category> mysqlCategories = categoryService.list(new QueryWrapper<Category>().eq("status", 1));
        List<Category> redisCategories = redisUtil.listRange("categories", 0, -1);
        for (Category mysqlCategory : mysqlCategories) {
            boolean flag = true;
            for (Category redisCategory : redisCategories) {
                if (redisCategory.getId().equals(mysqlCategory.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
               redisUtil.listRightPush("categories", mysqlCategory);
            }
        }
        List<Category> categories = redisUtil.listRange("categories", 0L, -1L);
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    @Test
    public void testInitHotDiscussionArticles() {
        LocalDate today = LocalDate.now();
        List<Article> list = articleService.list(new QueryWrapper<Article>()
                .ge("created", today)
                .ge("comment_count", 0));
        for (Article article : list) {
            redisUtil.zSetAdd(today, article, article.getCommentCount());
        }
        Long rank = redisTemplate.opsForZSet().rank(today, list.get(0));
        System.out.println(rank);
        Long rank1 = redisTemplate.opsForZSet().rank("2020-04-28", list.get(0));
        System.out.println(rank1);
    }


}
