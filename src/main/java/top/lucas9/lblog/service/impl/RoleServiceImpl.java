package top.lucas9.lblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.lucas9.lblog.entity.Role;
import top.lucas9.lblog.mapper.RoleMapper;
import top.lucas9.lblog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoles(Long userId) {
        return roleMapper.getRoles(userId);
    }
}
