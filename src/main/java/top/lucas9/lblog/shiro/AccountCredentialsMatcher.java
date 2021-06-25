package top.lucas9.lblog.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class AccountCredentialsMatcher implements CredentialsMatcher {
    /**
     * 已经采用 jwt 认证了，所以 shiro 的密码匹配就不需要了,直接返回 true
     * @param authenticationToken
     * @param authenticationInfo
     * @return true 密码匹配通过
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        return true;
    }
}
