package chat_server.hjs.domain.Weixin.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @className: MsgTypeVO
 * @author: wenzhuo4657
 * @date: 2024/9/8 15:57
 * @Version: 1.0
 * @description:    微信公众号消息类型值对象，用于描述对象属性的值，为值对象。
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MsgTypeVO {
    EVENT("event","事件消息"),
    TEXT("text","文本消息");

    private String code;
    private String desc;
}