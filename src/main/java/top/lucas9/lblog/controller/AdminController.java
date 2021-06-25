package top.lucas9.lblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.entity.UserFavorites;
import top.lucas9.lblog.entity.UserMessage;
import top.lucas9.lblog.vo.ArticleVo;

/**
 * @author lucas
 */
@RestController
@Api(tags = "管理员模块")
@RequestMapping("/admin")
public class AdminController extends BaseController {


    @RequiresRoles("admin")
    @GetMapping("/test")
    public Result test() {
        return Result.success("管理员");
    }

    @ApiOperation(value = "删除文章", notes = "登录且admin才能操作")
    @RequiresRoles("admin")
    @GetMapping("/delete/{articleId:\\d*}")
    public Result deleteArticle(@PathVariable("articleId") Long articleId) {
        Article dbArticle = articleService.getById(articleId);
        Assert.notNull(dbArticle, "该文章不存在");
        articleService.removeById(articleId);
        messageService.remove(new QueryWrapper<UserMessage>()
                .eq("article_id", articleId)
        );
        favoritesService.remove(new QueryWrapper<UserFavorites>()
                .eq("article_id", articleId)
        );
        return Result.success("删除成功");
    }

    @ApiOperation(value = "推荐/取消推荐", notes = "登录且admin才能操作")
    @RequiresRoles("admin")
    @GetMapping("/recommend/{articleId:\\d*}")
    public Result recommend(@PathVariable("articleId") Long articleId, Boolean recommend) {
        Article dbArticle = articleService.getById(articleId);
        Assert.notNull(dbArticle, "该文章不存在");
        dbArticle.setRecommend(recommend);
        articleService.updateById(dbArticle);
        return Result.success(recommend ? "推荐成功" : "取消推荐成功");
    }

    @ApiOperation(value = "置顶/取消置顶", notes = "登录且admin才能操作")
    @RequiresRoles("admin")
    @GetMapping("/top/{articleId:\\d*}")
    public Result top(@PathVariable("articleId") Long articleId, Boolean top) {
        Article dbArticle = articleService.getById(articleId);
        Assert.notNull(dbArticle, "该文章不存在");
        if (top) {
            Long maxLevel = articleService.selectMaxLevel();
            dbArticle.setLevel(maxLevel + 1);
        } else {
            dbArticle.setLevel(0L);
        }
        articleService.updateById(dbArticle);
        return Result.success(top ? "置顶成功" : "取消置顶成功");
    }

    @ApiOperation(value = "初始化ES", notes = "登录且admin才能操作")
    @RequiresRoles("admin")
    @GetMapping("/initEsData")
    public Result initEsData() {
        int size = 10000;
        Page page = new Page(1, size);
        IPage<ArticleVo> paging = articleService.paging(page, null, null, null, null, null);
        searchService.initEsData(paging.getRecords());
        long pages = paging.getPages();
        long total = paging.getTotal();
        for (int i = 2; i < pages; i ++) {
            page.setCurrent(i);
            paging = articleService.paging(page, null, null, null, null, null);
            searchService.initEsData(paging.getRecords());
        }
        return Result.success("ES索引初始化成功，共 " + total + " 条记录！");
    }
}
