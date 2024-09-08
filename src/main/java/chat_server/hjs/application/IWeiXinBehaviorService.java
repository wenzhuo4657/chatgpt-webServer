package chat_server.hjs.application;

import chat_server.hjs.domain.Weixin.model.enity.UserBehaviorMessageEntity;

/**
 * @className: IWeiXinBehaviorService
 * @author: wenzhuo4657
 * @date: 2024/9/8 15:29
 * @Version: 1.0
 * @description: weixin公众号受理用户行为
 */
public interface IWeiXinBehaviorService {
      /**
         *  des: 获取chatweb登录权限验证码
         * */
    String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity);
}