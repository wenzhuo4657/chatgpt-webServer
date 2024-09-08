package chat_server.hjs.interfaces;

import chat_server.hjs.Infrastructure.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response<String>doLogin(@RequestParam String     code){
        log.info("鉴权登录校验开始，验证码: {}", code);

        return  null;

    }
}