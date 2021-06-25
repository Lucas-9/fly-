package top.lucas9.lblog.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.entity.*;
import top.lucas9.lblog.service.UserMessageService;
import top.lucas9.lblog.service.UserService;
import top.lucas9.lblog.vo.UserMessageVo;

import java.time.LocalDateTime;

/**
 * @author lucas
 */
@Api(tags = "用户个人操作，需要认证后操作")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMessageService messageService;

    @RequiresAuthentication
    @ApiOperation(value = "获取个人信息")
    @PostMapping("selfInfo")
    public Result<AccountInfo> selfInfo() {
        User user = userService.getById(getSelfId());
        return Result.success(user);
    }

    @RequiresAuthentication
    @ApiOperation(value = "修改昵称和密码")
    @PostMapping("updateSelfInfo")
    public Result<AccountInfo> updateSelfInfo(@RequestBody User user) {
        if (null == user.getUsername() || "".equals(user.getUsername())) {
            return Result.failed("昵称不能为空");
        }
        if (!user.getId().equals(getSelfId())) {
            return Result.failed("非法操作");
        }
        int count = userService.count(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .ne("id", getSelfId()));
        if (count > 0) {
            return Result.failed("昵称已被占用");
        }
        User temp = userService.getById(getSelfId());
        if (temp == null || !temp.getPassword().equals(SecureUtil.md5(user.getPassword()))) {
            return Result.failed("原密码错误");
        }
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setModified(LocalDateTime.now());
        userService.updateById(temp);
        return Result.success("更新成功");
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取发布的文章")
    @PostMapping("/publish")
    public Result publish(@RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                          @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
        IPage page = articleService.page(new Page(pageNumber, pageSize), new QueryWrapper<Article>()
                .eq("user_id", getSelfId())
                .orderByDesc("created"));
        return Result.success(page);
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取收藏的文章")
    @PostMapping("/favorites")
    public Result favorites(@RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
        IPage page = articleService.page(new Page(pageNumber, pageSize), new QueryWrapper<Article>()
                .inSql("id", "SELECT `article_id` FROM `user_collection` where `user_id` = " + getSelfId())
        );
        return Result.success(page);
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取接收的消息", notes = "unread不传表示查所有，true表示查未读，false表示查已读")
    @PostMapping("/messages")
    public Result<IPage<UserMessageVo>> messages(@RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                 @RequestParam(value = "unread", required = false) Boolean unread) {
        IPage<UserMessageVo> page = messageService.paging(new Page(pageNumber, pageSize), new QueryWrapper<UserMessage>()
                .eq("user_id_of_receive", getSelfId())
                .eq(unread != null, "status", unread == null ? 0 : unread ? 1 : 0)
                .orderByDesc("created")
        );
        return Result.success(page);
    }

    @RequiresAuthentication
    @ApiOperation(value = "获取未读消息数目")
    @PostMapping("/message/unreadNumber")
    public Result<Integer> unreadNumber() {
        int count = messageService.count(new QueryWrapper<UserMessage>()
                .eq("status", 1)
                .eq("user_id_of_receive", getSelfId()));
        return Result.success(count);
    }


    @RequiresAuthentication
    @ApiOperation(value = "获取消息详情")
    @GetMapping("/message/{messageId:\\d*}")
    public Result<UserMessageVo> messageDetail(@PathVariable("messageId") Long messageId) {
        UserMessageVo message = messageService.selectOneMessage(messageId);
        messageService.updateRead(message.getId());
        return Result.success(message);
    }

    @RequiresAuthentication
    @ApiOperation(value = "删除消息", notes = "all为true则删除所有消息，all为false则删除指定id的消息")
    @PostMapping("/message/remove/")
    public Result msgRemove(@RequestParam(value = "messageId", required = false) Long messageId, @RequestParam(defaultValue = "false") Boolean all) {
        boolean remove = messageService.remove(new QueryWrapper<UserMessage>()
                .eq("user_id_of_receive", getSelfId())
                .eq(!all, "id", messageId));
        return remove ? Result.success("删除成功") : Result.failed("删除失败");
    }

    @RequiresAuthentication
    @ApiOperation(value = "判断登录用户是否收藏文章")
    @GetMapping("/favorites/select/{articleId:\\d*}")
    public Result<Boolean> isFavorite(@PathVariable("articleId") Long articleId) {
        int count = favoritesService.count(new QueryWrapper<UserFavorites>()
                .eq("user_id", getSelfId())
                .eq("article_id", articleId)
        );
        return Result.success(count > 0);
    }

    @RequiresAuthentication
    @ApiOperation(value = "收藏文章")
    @GetMapping("/favorites/add/{articleId:\\d*}")
    public Result add(@PathVariable("articleId") Long articleId) {
        Article article = articleService.getById(articleId);
        Assert.notNull(article, "该文章不存在");
        int count = favoritesService.count(new QueryWrapper<UserFavorites>()
                .eq("user_id", getSelfId())
                .eq("article_id", articleId)
        );
        if (count > 0) {
            return Result.failed("你已经收藏该文章");
        }
        UserFavorites userFavorites = new UserFavorites();
        userFavorites.setUserId(getSelfId());
        userFavorites.setArticleUserId(article.getUserId());
        userFavorites.setArticleId(articleId);
        userFavorites.setCreated(LocalDateTime.now());
        userFavorites.setModified(LocalDateTime.now());
        favoritesService.save(userFavorites);
        return Result.success("收藏成功");
    }

    @RequiresAuthentication
    @ApiOperation(value = "取消收藏")
    @GetMapping("/favorites/remove/{articleId:\\d*}")
    public Result remove(@PathVariable("articleId") Long articleId) {
        boolean remove = favoritesService.remove(new QueryWrapper<UserFavorites>()
                .eq("user_id", getSelfId())
                .eq("article_id", articleId)
        );
        return Result.success(remove ? "取消收藏成功" : "你未收藏该文章");
    }
}
