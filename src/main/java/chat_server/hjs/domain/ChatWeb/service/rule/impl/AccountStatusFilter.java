package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.model.valobj.UserAccountStatusVO;
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
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.ACCOUNT_STATUS)
public class AccountStatusFilter implements ILogicFilter<UserAccountQuotaEntity> {


    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {

         /**
            *  des: java中的枚举常量都是唯一的，
            * */
        if (UserAccountStatusVO.AVAILABLE.equals(data.getUserAccountStatusVO())){
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS).build();
        }

        return  RuleLogicEntity.<ChatProcessAggregate>builder()
                .info("您的账户已冻结，暂时不可使用。如果有疑问，可以联系客户解冻账户。")
                .type(LogicCheckTypeVO.REFUSE).data(chatProcess).build();
    }
}