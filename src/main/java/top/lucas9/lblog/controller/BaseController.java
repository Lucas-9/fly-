package top.lucas9.lblog.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import top.lucas9.lblog.entity.AccountInfo;
import top.lucas9.lblog.service.*;
import top.lucas9.lblog.utils.RedisUtil;


/**
 * @author lucas
 */
public class BaseController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserMessageService messageService;
    @Autowired
    UserFavoritesService favoritesService;
    @Autowired
    UserService userService;
    @Autowired
    SearchService searchService;
    @Autowired
    AmqpTemplate amqpTemplate;

    protected Long getSelfId() {
        AccountInfo accountInfo = (AccountInfo) SecurityUtils.getSubject().getPrincipal();
        if (null == accountInfo) {
            throw new ShiroException("请登录后操作");
        }
        return accountInfo.getId();
    }
}
