package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: ModelTypeFilter
 * @author: wenzhuo4657
 * @date: 2024/9/11 17:54
 * @Version: 1.0
 * @description: 模型请求匹配校验
 */
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.MODEL_TYPE)
public class ModelTypeFilter implements ILogicFilter<UserAccountQuotaEntity> {
    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {

        List<String> allowModelTypeList = data.getAllowModelTypeList();
        String model = chatProcess.getModel();

        if (allowModelTypeList.contains(model)){
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.REFUSE)
                    .data(chatProcess)
                    .build();
        }

        return RuleLogicEntity.<ChatProcessAggregate>builder()
                .type(LogicCheckTypeVO.REFUSE)
                .info("当前账户不支持使用 " + model + " 模型！可以联系客服升级账户。")
                .data(chatProcess)
                .build();
    }
}