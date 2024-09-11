package chat_server.hjs.domain.ChatWeb.service;

import ToOne.chatglm_sdk_master.model.RequestSSE;
import ToOne.chatglm_sdk_master.model.ResponseStream;
import chat_server.hjs.Infrastructure.Exception.ChatGPTException;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.Infrastructure.model.valobj.Constants;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.repository.IChatWebRepository;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: ChatWebService
 * @author: wenzhuo4657
 * @date: 2024/9/8 8:56
 * @Version: 1.0
 * @description:
 */
@Service("IChatWebService")
public class ChatWebService extends AbstractChatWebService {
    Logger log = LoggerFactory.getLogger(ChatWebService.class);

    @Autowired
    public ChatWebService(IChatWebRepository chatWebRepository) {
        super(chatWebRepository);
    }

    @Override
    protected RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, UserAccountQuotaEntity userAccountQuotaEntity, String... logics) throws Exception {
        Map<String, ILogicFilter> logicFilterMap = defaultLogicFactory.getLogicFilterMap();
        RuleLogicEntity<ChatProcessAggregate> entity=null;

        for (String code :logics){
            if (DefaultLogicFactory.LogicModel.NULL.getCode().equals(code)) continue;
            entity = logicFilterMap.get(code).filter(chatProcess,userAccountQuotaEntity);
            if (!LogicCheckTypeVO.SUCCESS.getCode().equals(entity.getType())){
                return  entity;
            }
        }

//        注意这个三元表达式，entity只会在没有对应过滤器是返回一个null,所以此处返回一个成功的校验。
        return entity!=null? entity:RuleLogicEntity.<ChatProcessAggregate>builder()
                .type(LogicCheckTypeVO.SUCCESS).data(chatProcess).build();
    }

    @Resource
    private DefaultLogicFactory defaultLogicFactory;




    @Override
    protected void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter responseBodyEmitter) throws JsonProcessingException {
        List<RequestSSE.Message> messages = chatProcess.getMessages().stream()
                .map(enity -> RequestSSE.Message.builder().role(enity.getRole()).content(enity.getContent())
                .build()).collect(Collectors.toList());

        RequestSSE request = RequestSSE.builder()
                .stream(true)
                .messages(messages)
                .model(chatProcess.getModel())
                .build();

        try {
            openAiSession.completionsStream(request, new EventSourceListener() {
                @Override
                public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                    if ("[DONE]".equals(data)) {
                        log.info("[输出结束] Tokens {}", JSON.toJSONString(data));
                        return;
                    }
                    ResponseStream response = JSON.parseObject(data, ResponseStream.class);
                    List<ResponseStream.Choice> choices = response.getChoices();
                    for (ResponseStream.Choice chatChoice:choices){

                        ResponseStream.Delta message = chatChoice.getDelta();
                        if (Objects.isNull(message)){
                            continue;
                        }

                        String finisReason=chatChoice.getFinishReason();
                        if (StringUtils.isNoneBlank(finisReason)&& "stop".equals(finisReason)){
                            responseBodyEmitter.complete();
                            break;
                        }
                        try {
                            responseBodyEmitter.send(message.getContent());
                        } catch (Exception e) {
                            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(),Constants.ResponseCode.UN_ERROR.getInfo());
                        }
                    }

                }

                @Override
                public void onClosed(EventSource eventSource) {
                    log.info("对话完成");

                }

                @Override
                public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                    log.error("对话失败", t);

                }
            });
        } catch (IOException e) {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }
}