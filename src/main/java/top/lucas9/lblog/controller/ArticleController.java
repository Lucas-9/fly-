package top.lucas9.lblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.config.RabbitConfig;
import top.lucas9.lblog.entity.*;
import top.lucas9.lblog.search.mq.ArticleMqMessage;
import top.lucas9.lblog.vo.ArticleVo;
import top.lucas9.lblog.vo.CommentVo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@RestController
@Api(tags = "文章操作")
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @ApiOperation(value = "根据关键字搜索文章")
    @GetMapping("/search")
    public Result<IPage<ArticleVo>> search(@RequestParam("keyword") String keyword,
                                           @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                           @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        IPage<ArticleVo> pageData = searchService.search(pageNumber, pageSize, keyword);
        return Result.success(pageData);
    }

    @ApiOperation(value = "获取文章列表", notes = "可传分类id获取指定分类下的文章")
    @PostMapping("/articles")
    public Result<IPage<ArticleVo>> articles(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
        // 1分页 2分类 3用户 4置顶  5精选 6排序
        IPage<ArticleVo> results = articleService.paging(new Page(pageNumber, pageSize), categoryId, null, null, null, "created");
        return Result.success(results);
    }

    @ApiOperation(value = "获取置顶的文章，按置顶等级排序", notes = "可传分类id获取指定分类下的文章")
    @PostMapping("/topArticles")
    public Result<IPage<ArticleVo>> topArticles(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
        IPage<ArticleVo> results = articleService.paging(new Page(pageNumber, pageSize), categoryId, null, 1, null, "level");
        return Result.success(results);
    }

    @ApiOperation(value = "根据id查询文章信息，id必传")
    @GetMapping("/{articleId:\\d*}")
    public Result<ArticleVo> article(@PathVariable("articleId") Long articleId) {
        ArticleVo articleVo = articleService.selectOneArticle(new QueryWrapper<Article>().eq("a.id", articleId));
        Assert.notNull(articleVo, "该文章不存在");
        articleService.updateViewCount(articleVo);
        return Result.success(articleVo);
    }

    @ApiOperation(value = "根据id查询评论信息，id必传")
    @PostMapping("/comments")
    public Result<IPage<CommentVo>> comment(
            @RequestParam(value = "articleId") Long articleId,
            @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
        IPage<CommentVo> commentVoIPage = commentService.paging(new Page(pageNumber, pageSize), articleId, null, "created");
        return Result.success(commentVoIPage);
    }

    @ApiOperation(value = "查询当日评论最多的文章")
    @GetMapping("hotCommentArticlesOfToDay")
    public Result<List<ArticleVo>> hotCommentArticlesOfToDay() {
        List list = articleService.selectHotCommentArticlesOfToDay();
        return Result.success(list);
    }

    @RequiresAuthentication
    @ApiOperation(value = "新增或修改文章", notes = "登录且本人才能操作")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@Valid @RequestBody Article article) {
        if (null == article.getId()) {
            article.setUserId(getSelfId());
            article.setRecommend(false);
            article.setStatus(1);
            article.setCreated(LocalDateTime.now());
            article.setModified(LocalDateTime.now());
            articleService.save(article);
        } else {
            Article dbArticle = articleService.getById(article.getId());
            Assert.notNull(dbArticle, "该文章不存在");
            Assert.isTrue(dbArticle.getUserId().equals(getSelfId()), "无权修改此文章");
            dbArticle.setTitle(article.getTitle());
            dbArticle.setContent(article.getContent());
            dbArticle.setCategoryId(article.getCategoryId());
            dbArticle.setModified(LocalDateTime.now());
            articleService.updateById(dbArticle);
        }
        amqpTemplate.convertAndSend(Constant.MQ_EXCHANGE, Constant.MQ_BIND_KEY,
                new ArticleMqMessage(article.getId(), Constant.MESSAGE_CREATE_OR_UPDATE));
        return Result.success("操作成功");
    }

    @RequiresAuthentication
    @ApiOperation(value = "删除文章", notes = "登录且本人才能操作")
    @GetMapping("/delete/{articleId:\\d*}")
    public Result delete(@PathVariable("articleId") Long articleId) {
        Article dbArticle = articleService.getById(articleId);
        Assert.notNull(dbArticle, "该文章不存在");
        Assert.isTrue(dbArticle.getUserId().equals(getSelfId()), "无权删除此文章");
        articleService.removeById(articleId);
        messageService.remove(new QueryWrapper<UserMessage>()
                .eq("article_id", articleId)
        );
        favoritesService.remove(new QueryWrapper<UserFavorites>()
                .eq("article_id", articleId)
        );
        amqpTemplate.convertAndSend(Constant.MQ_EXCHANGE, Constant.MQ_BIND_KEY,
                new ArticleMqMessage(articleId, Constant.MESSAGE_REMOVE));
        return Result.success("删除成功");
    }


    @RequiresAuthentication
    @ApiOperation(value = "评论", notes = "登录才能操作")
    @PostMapping("/replay")
    public Result replay(Long articleId, String content) {
        Assert.notNull(articleId, "找不到对应的文章");
        Assert.hasLength(content, "评论内容不能为空");
        Article article = articleService.getById(articleId);
        Assert.notNull(article, "该文章不存在");

        // 评论
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setUserId(getSelfId());
        comment.setCreated(LocalDateTime.now());
        comment.setModified(LocalDateTime.now());
        comment.setLevel(0);
        comment.setVoteDown(0);
        comment.setVoteUp(0);
        commentService.save(comment);

        // 评论数量加一
        article.setCommentCount(article.getCommentCount() + 1);
        articleService.updateById(article);
        String format = article.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 本日热议榜单数量加一
        if (format.equals(LocalDate.now().toString())) {
            articleService.plusCommentCount(LocalDate.now() ,articleId, true);
        }

        // 消息
        UserMessage message = new UserMessage();
        message.setArticleId(articleId);
        message.setCommentId(comment.getId());
        message.setUserIdOfSend(getSelfId());
        message.setStatus(1);
        message.setCreated(LocalDateTime.now());
        if(comment.getUserId().equals(article.getUserId())) {
            message.setType(1);
            message.setUserIdOfReceive(article.getUserId());
            message.setContent(content);
        }

        if(content.startsWith("@")) {
            int index = content.indexOf(" ");
            String username = content.substring(1,index);
            content = content.substring(index + 1);
            User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
            if (user != null) {
                message.setUserIdOfReceive(user.getId());
                message.setType(2);
                message.setContent(content);
            }
        }
        messageService.save(message);
        return Result.success("评论成功");
    }
}
