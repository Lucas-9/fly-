package top.lucas9.lblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.lucas9.lblog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lucas9.lblog.vo.ArticleVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface ArticleMapper extends BaseMapper<Article> {
    IPage<ArticleVo> selectArticles(Page page, @Param(Constants.WRAPPER) QueryWrapper<Article> detailQueryWrapper);

    ArticleVo selectOneArticle(@Param(Constants.WRAPPER) QueryWrapper<Article> wrapper);

    @Select("select max(`level`) from `article`")
    Long selectMaxLevel();
}
