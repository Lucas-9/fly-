package top.lucas9.lblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.lucas9.lblog.entity.Category;
import top.lucas9.lblog.mapper.CategoryMapper;
import top.lucas9.lblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lucas9.lblog.utils.RedisUtil;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private RedisUtil redisUtil;
    public void initCategoriesCache() {
        List<Category> mysqlCategories = this.list(new QueryWrapper<Category>().eq("status", 1));
        // 存入redis
        List<Category> redisCategories = redisUtil.listRange("categories", 0L, -1L);
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
    }

}
