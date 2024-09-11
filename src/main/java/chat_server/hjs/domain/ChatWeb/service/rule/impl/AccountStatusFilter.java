package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import org.springframework.stereotype.Component;

/**
 * @className: AccountStatusFilter
 * @author: wenzhuo4657
 * @date: 2024/9/11 11:11
 * @Version: 1.0
 * @description:
 */
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.USER_QUOTA)
public class AccountStatusFilter implements ILogicFilter<UserAccountQuotaEntity> {


    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {
        return null;
    }
}