package top.lucas9.lblog.service.impl;

import top.lucas9.lblog.entity.UserFavorites;
import top.lucas9.lblog.mapper.UserFavoritesMapper;
import top.lucas9.lblog.service.UserFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-22
 */
@Service
public class UserFavoritesServiceImpl extends ServiceImpl<UserFavoritesMapper, UserFavorites> implements UserFavoritesService {

}
