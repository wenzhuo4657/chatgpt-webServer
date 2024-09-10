package chat_server.hjs.domain.ChatWeb.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @className: LogicCheckTypeVO
 * @author: wenzhuo4657
 * @date: 2024/9/10 10:25
 * @Version: 1.0
 * @description:    逻辑校验类型，值对象
 */
@Getter
@AllArgsConstructor
public enum LogicCheckTypeVO {


    SUCCESS("0000", "校验通过"),
    REFUSE("0001","校验拒绝"),
            ;

    private final String code;
    private final String info;
}