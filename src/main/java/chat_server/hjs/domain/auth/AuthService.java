package chat_server.hjs.domain.auth;

import chat_server.hjs.domain.auth.enity.AuthStateEntity;
import chat_server.hjs.domain.auth.valobj.AuthTypeVO;
import com.google.common.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: AuthService
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:27
 * @Version: 1.0
 * @description:
 */
@Service("authService")
public class AuthService extends  AbstractAuthService{
    private Logger log= LoggerFactory.getLogger(AuthService.class);


    @Resource
    private Cache<String,String> codeCache;




    @Override
    public boolean checkToken(String token) {
        return isVerify(token);
    }

    @Override
    protected AuthStateEntity checkCode(String code) {

        String openId = codeCache.getIfPresent(code);
        if (StringUtils.isBlank(openId)){
            log.info("鉴权，用户收入的验证码不存在 {}", code);
            return AuthStateEntity.builder()
                    .code(AuthTypeVO.A0001.getCode())
                    .info(AuthTypeVO.A0001.getInfo())
                    .build();
        }


        codeCache.invalidate(openId);
        codeCache.invalidate(code);


        return AuthStateEntity.builder()
                .code(AuthTypeVO.A0000.getCode())
                .info(AuthTypeVO.A0000.getInfo())
                .openId(openId)
                .build();

    }
}