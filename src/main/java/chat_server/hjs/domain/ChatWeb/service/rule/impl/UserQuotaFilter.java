package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.repository.IChatWebRepository;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import org.springframework.stereotype.Component;

/**
 * @className: UserQuotaFilter
 * @author: wenzhuo4657
 * @date: 2024/9/11 18:01
 * @Version: 1.0
 * @description:
 */
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.USER_QUOTA)
public class UserQuotaFilter implements ILogicFilter<UserAccountQuotaEntity> {

    private IChatWebRepository chatWebRepository;

    public UserQuotaFilter(IChatWebRepository chatWebRepository) {
        this.chatWebRepository = chatWebRepository;
    }

    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {

          /**
             *  des:
           *  该部分代码似乎有些臃肿，但是如果从数据库层面信息和java内存信息不一致的角度考虑，多重校验的目的似乎是为了安全性而牺牲了效率，
           *  第一次额度校验在内存中获取的data的额度，第二次额度校验是在sql语句中，这里可以看出，代码的逻辑是将sql执行的成功与否当做最后一道防线。
           *  从执行效率的角度看，data做了第一层过滤，sql执行做了第二层过滤，降低了效率。
           *  但是假如从服务和支付的角度来看，这里是为了尽量保证安全性，而且对于gpt服务来说调用频率是相当高的。
           *
           *  这里更多的提醒我可以使用内存和数据库两个层面来更新状态。
             * */
        if (data.getSurplusQuota()>0){
            int updateCount =chatWebRepository.subAccountQuota(data.getOpenid());
            if(updateCount!=0){
                return RuleLogicEntity.<ChatProcessAggregate>builder()
                        .type(LogicCheckTypeVO.SUCCESS).data(chatProcess).build();
            }
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .info("个人账户，总额度【" + data.getTotalQuota() + "】次，已耗尽！")
                    .type(LogicCheckTypeVO.REFUSE).data(chatProcess).build();

        }




        return RuleLogicEntity.<ChatProcessAggregate>builder()
                .info("个人账户，总额度【" + data.getTotalQuota() + "】次，已耗尽！")
                .type(LogicCheckTypeVO.REFUSE).data(chatProcess).build();
    }
}