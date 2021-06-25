package top.lucas9.lblog.service;

import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lucas9.lblog.entity.AccountInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountInfo login(String username, String password, HttpServletResponse response);
}
