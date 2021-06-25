package top.lucas9.lblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.lucas9.lblog.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lucas9
 * @since 2021-06-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoles(@Param("userId") Long userId);
}
