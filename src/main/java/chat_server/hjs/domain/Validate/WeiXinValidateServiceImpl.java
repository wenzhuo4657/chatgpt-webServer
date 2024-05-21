package chat_server.hjs.domain.Validate;

import chat_server.hjs.Infrastructure.util.SignatureUtil;
import chat_server.hjs.application.IWeiXinValidateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

/**
 * @className: WeiXinValidateServiceImpl
 * @author: wenzhuo4657
 * @date: 2024/5/21 18:38
 * @Version: 1.0
 * @description:
 */
@Service(value = "WeiXinValidateService")
public class WeiXinValidateServiceImpl  implements IWeiXinValidateService {

    @Value("${wx.config.token}")
    private String token;

    @Override
    public boolean checkSign(String signature, String timestamp, String nonce) {
        return SignatureUtil.check(token, signature, timestamp, nonce);
    }
}