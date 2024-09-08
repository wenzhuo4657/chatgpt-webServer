package chat_server.hjs.application;

import chat_server.hjs.domain.auth.enity.AuthStateEntity;

/**
 * @className: IAuthService
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:26
 * @Version: 1.0
 * @description: 鉴权验证服务
 */
public interface IAuthService {

    /**
     * 登录验证
     * @param code 验证码
     * @return Token
     */
    AuthStateEntity doLogin(String code);

    boolean checkToken(String token);
}