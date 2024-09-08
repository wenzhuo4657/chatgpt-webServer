package chat_server.hjs.domain.auth;

import chat_server.hjs.Infrastructure.common.AuthEnum;
import chat_server.hjs.application.IAuthService;
import chat_server.hjs.domain.auth.enity.AuthStateEntity;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: AbstractAuthService
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:39
 * @Version: 1.0
 * @description:
 */
public abstract class AbstractAuthService implements IAuthService {

    private Logger log = LoggerFactory.getLogger(AbstractAuthService.class);
    private static final String defaultBase64EncodedSecretKey = AuthEnum.defaultBase64EncodedSecretKey;

    private final String base64EncodedSecretKey = Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes());

    private final Algorithm algorithm= Algorithm.HMAC256(Base64.decodeBase64(Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes())));

    @Override
    public AuthStateEntity doLogin(String code) {
        return null;
    }
}