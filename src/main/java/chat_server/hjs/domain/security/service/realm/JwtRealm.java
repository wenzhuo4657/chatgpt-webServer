package chat_server.hjs.domain.security.service.realm;

import chat_server.hjs.domain.security.model.vo.JwtToken;
import chat_server.hjs.domain.security.service.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @className: JwtRealm
 * @author: wenzhuo4657
 * @date: 2024/5/18 16:16
 * @Version: 1.0
 * @description: 自定义shiro验证服务，
 */
public class JwtRealm extends AuthorizingRealm {
    private Logger log= LoggerFactory.getLogger(JwtRealm.class);
    private static JwtUtil jwtUtil = JwtUtil.DefaultJwtUtil();
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }





//    token验证服务
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt=(String)token.getPrincipal();
        if (Objects.isNull(jwt)){
            throw new NullPointerException("jwtToken 不允许为空");
        }
        if(!jwtUtil.isVerify(jwt)){
            throw new UnknownAccountException();
        }
        String username = (String) jwtUtil.decode(jwt).get("username");
        log.info("鉴权用户 username：{}", username);
        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");
    }
}