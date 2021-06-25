package top.lucas9.lblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.lucas9.lblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lucas9.lblog.vo.CommentVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface CommentMapper extends BaseMapper<Comment> {
    IPage<CommentVo> selectComments(Page page, @Param(Constants.WRAPPER) QueryWrapper<Comment> wrapper);
}
