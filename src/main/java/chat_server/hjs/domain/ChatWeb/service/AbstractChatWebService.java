package chat_server.hjs.domain.ChatWeb.service;

import ToOne.chatglm_sdk_master.session.OpenAiSession;
import chat_server.hjs.Infrastructure.Exception.ChatGPTException;
import chat_server.hjs.Infrastructure.util.ChatglmUtils;
import chat_server.hjs.application.IChatWebService;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.Constants;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.service.rule.factory.DefaultLogicFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @className: ChatWebService
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:16
 * @Version: 1.0
 * @description:
 */

public abstract class AbstractChatWebService implements IChatWebService {


    protected static OpenAiSession openAiSession = ChatglmUtils.getSession();

    private Logger log = LoggerFactory.getLogger(AbstractChatWebService.class);

    /**
     * des: 应答的模版方法
     */
    @Override
    public ResponseBodyEmitter completions(ResponseBodyEmitter emitter, ChatProcessAggregate chatProcess) {

        try {
            emitter.onCompletion(() -> {
                log.info("应答未完成，使用模型{}", chatProcess.getModel());
            });
            emitter.onError(throwable -> {
                log.info("应答出错，使用模型{}", chatProcess.getModel(), throwable);
            });


            RuleLogicEntity<ChatProcessAggregate> entity = this.doCheckLogic(chatProcess,
                    DefaultLogicFactory.LogicModel.ACCESS_LIMIT.getCode(),
                    DefaultLogicFactory.LogicModel.SENSITIVE_WORD.getCode());

            if (!LogicCheckTypeVO.SUCCESS.getCode().equals(entity.getType())){
                    emitter.send(entity.getInfo());
                    emitter.complete();
                    return emitter;
            }

            this.doMessageResponse(chatProcess, emitter);
        } catch (Exception e) {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

        return emitter;
    }

    protected abstract void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter responseBodyEmitter) throws JsonProcessingException;

    protected abstract RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, String... logics) throws Exception;

}