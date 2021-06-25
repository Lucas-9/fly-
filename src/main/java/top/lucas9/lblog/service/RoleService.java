package top.lucas9.lblog.service;

import top.lucas9.lblog.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-20
 */
public interface RoleService extends IService<Role> {

    List<String> getRoles(Long userId);
}
