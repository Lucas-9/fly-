package top.lucas9.lblog.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.controller.BaseController;
import top.lucas9.lblog.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类")
public class CategoryController extends BaseController {

    @ApiOperation(value = "获取分类列表")
    @GetMapping("/categories")
    public Result categories() {
        // 从redis中查询
        List<Category> categories = redisUtil.listRange("categories", 0, -1);
        return Result.success(categories);
    }
}
