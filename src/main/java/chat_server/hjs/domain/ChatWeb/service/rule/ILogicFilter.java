package chat_server.hjs.domain.ChatWeb.service.rule;

import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;

/**
 * @className: ILogicFilter
 * @author: wenzhuo4657
 * @date: 2024/9/10 10:22
 * @Version: 1.0
 * @description: 规则过滤器
 */
public interface ILogicFilter<T> {
    RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception;

}