package chat_server.hjs.domain.Weixin.service.message;

import chat_server.hjs.Infrastructure.Exception.ChatGPTException;
import chat_server.hjs.Infrastructure.util.XmlUtil;
import chat_server.hjs.application.IWeiXinBehaviorService;
import chat_server.hjs.domain.Weixin.model.enity.MessageTextEntity;
import chat_server.hjs.domain.Weixin.model.enity.UserBehaviorMessageEntity;
import chat_server.hjs.domain.Weixin.model.valobj.MsgTypeVO;
import com.google.common.cache.Cache;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: WeiXinBehaviorService
 * @author: wenzhuo4657
 * @date: 2024/9/8 15:34
 * @Version: 1.0
 * @description:
 */
@Service("weiXinBehaviorService")
public class WeiXinBehaviorService implements IWeiXinBehaviorService {

    @Value("${wx.config.originalid}")
    private String originalId;

    @Resource
    private Cache<String, String> codeCache;

    @Override
    public String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity) {
        if (MsgTypeVO.EVENT.getCode().equals(userBehaviorMessageEntity.getMsgType())) {
            return "";
        }

//        if (MsgTypeVO.TEXT.equals(userBehaviorMessageEntity.getMsgType())) {
            String isExistCode = codeCache.getIfPresent(userBehaviorMessageEntity.getOpenId());

            if (StringUtils.isBlank(isExistCode)) {
                String code= RandomStringUtils.randomNumeric(4);
                codeCache.put(code, userBehaviorMessageEntity.getOpenId());
                codeCache.put(userBehaviorMessageEntity.getOpenId(), code);
                isExistCode = code;
            }

            MessageTextEntity res = new MessageTextEntity();
            res.setToUserName(userBehaviorMessageEntity.getOpenId());
            res.setFromUserName(originalId);
            res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
            res.setMsgType("text");
            res.setContent(String.format("您的验证码为：%s 有效期%d分钟！", isExistCode, 3));
            return XmlUtil.beanToXml(res);
//        }


//        throw new ChatGPTException(userBehaviorMessageEntity.getMsgType() + " 未被处理的行为类型 Err！");
    }
}