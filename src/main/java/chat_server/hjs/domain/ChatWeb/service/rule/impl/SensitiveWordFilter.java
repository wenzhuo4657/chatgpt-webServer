package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.MessageEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @className: SensitiveWordFilter
 * @author: wenzhuo4657
 * @date: 2024/9/10 11:19
 * @Version: 1.0
 * @description:  敏感关键词过滤
 * 注意：白名单用户不受该约束
 */
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.SENSITIVE_WORD)
public class SensitiveWordFilter implements ILogicFilter<UserAccountQuotaEntity> {

    private Logger log= LoggerFactory.getLogger(SensitiveWordFilter.class);

    @Resource
    private SensitiveWordBs words;

    @Value("${app.config.white-list}")
    private  String  whitelist;

    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {
        return null;
    }

    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess) throws Exception {
        if (chatProcess.isWhiteList(whitelist)){
            return  RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS).build();
        }

//        此处小傅哥选择新建对象填充，这里猜测大概是想要便于后期维护，对过滤前后前的message都有一个拓展点
//        ，且注意streamapi是对流中元素进行过滤后收集，在此处也没有修改流中原本的元素，意味着没有改变chatprocess
        ChatProcessAggregate newChatprocessAggregate=new ChatProcessAggregate();
        newChatprocessAggregate.setOpenid(chatProcess.getOpenid());
        newChatprocessAggregate.setModel(chatProcess.getModel());
        newChatprocessAggregate.setMessages(
                chatProcess.getMessages().stream()
                        .map(message->{
                            String content= message.getContent();
                            String replace=words.replace(content);
                            return MessageEntity.builder()
                                    .setRole(message.getRole())
                                    .setName(message.getName())
                                    .setContent(replace)
                                    .build();
                        }).collect(Collectors.toList()));



        return  RuleLogicEntity.<ChatProcessAggregate>builder()
                .type(LogicCheckTypeVO.SUCCESS)
                .data(newChatprocessAggregate)
                .build();
    }
}