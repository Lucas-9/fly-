package top.lucas9.lblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import top.lucas9.lblog.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lucas9.lblog.vo.UserMessageVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    IPage<UserMessageVo> selectMessages(Page page, @Param(Constants.WRAPPER) QueryWrapper<UserMessage> wrapper);

    UserMessageVo selectOneMessage(@Param("messageId") Long messageId);

    @Update("update `user_message` set `status` = 0 where `id` = #{messageId}")
    void updateRead(@Param("messageId") Long messageId);
}
