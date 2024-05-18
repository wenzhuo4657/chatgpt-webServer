package chat_server.hjs.domain.security.model.vo;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @className: JwtToken
 * @author: wenzhuo4657
 * @date: 2024/5/18 16:40
 * @Version: 1.0
 * @description:
 */
public class JwtToken  implements AuthenticationToken {
    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    /**
     * 等同于账户
     */
    @Override
    public Object getPrincipal() {
        return jwt;
    }

    /**
     * 等同于密码
     */
    @Override
    public Object getCredentials() {
        return jwt;
    }
}