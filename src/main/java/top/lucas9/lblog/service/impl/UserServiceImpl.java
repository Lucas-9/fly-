package top.lucas9.lblog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.Assert;
import top.lucas9.lblog.common.Result;
import top.lucas9.lblog.entity.User;
import top.lucas9.lblog.entity.AccountInfo;
import top.lucas9.lblog.mapper.UserMapper;
import top.lucas9.lblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lucas9.lblog.utils.JwtUtil;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lucas9
 * @since 2021-06-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User user) {
        int count = this.count(new QueryWrapper<User>()
                .eq("email", user.getEmail())
                .or()
                .eq("username", user.getUsername())
        );
        if(count > 0) {
            return Result.failed("用户名或邮箱已被占用");
        }
        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setEmail(user.getEmail());
        temp.setAvatar(user.getAvatar() == null ? "" : user.getAvatar());
        temp.setCreated(LocalDateTime.now());
        this.save(temp);
        return Result.success("注册成功");
    }

    @Override
    public AccountInfo login(String username, String password, HttpServletResponse response) {
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        Assert.notNull(user, "用户名或密码错误");
        if(!user.getPassword().equals(SecureUtil.md5(password))) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        String jwt = JwtUtil.createToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(user.getId());
        accountInfo.setAvatar(user.getAvatar());
        accountInfo.setUsername(user.getUsername());
        accountInfo.setEmail(user.getEmail());
        accountInfo.setToken(jwt);
        return accountInfo;
    }
}
