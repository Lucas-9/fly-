package top.lucas9.lblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.lucas9.lblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lucas9.lblog.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface CommentService extends IService<Comment> {
    IPage<CommentVo> paging(Page page, Long articleId, Long userId, String order);
}
