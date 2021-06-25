package top.lucas9.lblog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Calendar;

/**
 * @author lucas
 */
@Component
public class JwtUtil {
    public static String SECRET;
    private static Integer EXPIRE;

    @Value("${lucas9.jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }
    @Value("${lucas9.jwt.expire}")
    public void setExpire(Integer expire) {
        EXPIRE = expire;
    }

    /**
     * 生成 token header.payload.signature
     * @param userId
     * @return 设置用户名和过期时间生成的 token
     */
    public static String createToken(Long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("userId", userId);
        builder.withExpiresAt(calendar.getTime());
        String token = builder.sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 校验 token 的有效性
     * @param token
     * @return 有效返回 true， 无效返回 false
     */
    public static boolean verify(String token){
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *  获取解析后的 jwt
     * @param token 令牌
     * @return 获取的解析后的 jwt
     */

    public static DecodedJWT getTokenInfo(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            return verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new ShiroException("登录过期");
        } catch (Exception e) {
            throw new ShiroException("非法操作");
        }
    }

    /**
     * 从 token 中获取 userId
     * @param token 令牌
     * @return 获取的 userId
     */
    public static Long getUserId(String token) {
        DecodedJWT tokenInfo = getTokenInfo(token);
        return tokenInfo.getClaim("userId").asLong();
    }

}
