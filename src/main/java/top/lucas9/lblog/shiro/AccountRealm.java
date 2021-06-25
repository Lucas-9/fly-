package top.lucas9.lblog.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.lucas9.lblog.entity.AccountInfo;
import top.lucas9.lblog.service.RoleService;
import top.lucas9.lblog.utils.JwtUtil;

import java.util.List;

/**
 * @author lucas
 */
@Slf4j
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private RoleService roleService;


    /**
     * 支持我们的 jwt
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("=====================授权=========================");
        AccountInfo accountInfo = (AccountInfo) principals.getPrimaryPrincipal();
        List<String> roles = roleService.getRoles(accountInfo.getId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwtToken = ((JwtToken) token).getPrincipal();
        Long userId = JwtUtil.getUserId(jwtToken);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(userId);
        return new SimpleAuthenticationInfo(accountInfo, null, this.getName());
    }
}
