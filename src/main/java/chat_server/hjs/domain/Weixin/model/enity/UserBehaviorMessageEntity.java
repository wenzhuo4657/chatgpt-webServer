package chat_server.hjs.domain.Weixin.model.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @className: UserBehaviorMessageEntity
 * @author: wenzhuo4657
 * @date: 2024/9/8 15:34
 * @Version: 1.0
 * @description: 用户行为信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorMessageEntity {

    private String openId;
    private String fromUserName;
    private String msgType;
    private String content;
    private String event;
    private Date createTime;

}