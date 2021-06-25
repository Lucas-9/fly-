package top.lucas9.lblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import top.lucas9.lblog.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lucas9.lblog.vo.UserMessageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface UserMessageService extends IService<UserMessage> {

    IPage<UserMessageVo> paging(Page page, QueryWrapper<UserMessage> wrapper);

    UserMessageVo selectOneMessage(Long messageId);


    void updateRead(Long messageId);
}
