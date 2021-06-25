package top.lucas9.lblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import top.lucas9.lblog.entity.Comment;
import top.lucas9.lblog.mapper.CommentMapper;
import top.lucas9.lblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lucas9.lblog.vo.CommentVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public IPage<CommentVo> paging(Page page, Long articleId, Long userId, String order) {
        System.out.println(articleId);
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq(null != articleId, "article_id", articleId)
                .eq(null != userId, "user_id", userId)
                .orderByDesc(null != order, order);
        return commentMapper.selectComments(page, wrapper);
    }
}
