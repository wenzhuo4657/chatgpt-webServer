package chat_server.hjs.interfaces;

import chat_server.hjs.Infrastructure.model.enity.Response;
import chat_server.hjs.application.IAuthService;
import chat_server.hjs.Infrastructure.model.valobj.Constants;
import chat_server.hjs.domain.auth.enity.AuthStateEntity;
import chat_server.hjs.domain.auth.valobj.AuthTypeVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @className: AuthController
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:22
 * @Version: 1.0
 * @description:
 */
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
public class AuthController {

    Logger log = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private IAuthService authService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response<String>doLogin(@RequestParam String     code){
        log.info("鉴权登录校验开始，验证码: {}", code);
        try{
            AuthStateEntity authStateEntity = authService.doLogin(code);
            log.info("鉴权登录校验完成，验证码: {} 结果: {}", code, JSON.toJSONString(authStateEntity));
            if (!AuthTypeVO.A0000.getCode().equals(authStateEntity.getCode())) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.TOKEN_ERROR.getCode())
                        .info(Constants.ResponseCode.TOKEN_ERROR.getInfo())
                        .build();
            }
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(authStateEntity.getToken())
                    .build();

        } catch (Exception e) {
            log.error("鉴权登录校验失败，验证码: {}", code);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }

    }
}