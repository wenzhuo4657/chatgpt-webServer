package chat_server.hjs.domain.ChatWeb;

import ToOne.chatglm_sdk_master.session.OpenAiSession;
import chat_server.hjs.Infrastructure.Exception.ChatGPTException;
import chat_server.hjs.domain.ChatWeb.valobj.Constants;
import chat_server.hjs.Infrastructure.util.ChatglmUtils;
import chat_server.hjs.application.IChatWebService;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @className: ChatWebService
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:16
 * @Version: 1.0
 * @description:
 */

public abstract class AbstractChatWebService implements IChatWebService {

    @Value("${chat.config.pers}")
    public String pers;

    protected static OpenAiSession openAiSession = ChatglmUtils.getSession();

    private Logger log= LoggerFactory.getLogger(AbstractChatWebService.class);

    @Override
    public ResponseBodyEmitter completions(ChatProcessAggregate chatProcess) {
        if (!pers.equals(chatProcess.getToken())) {
            throw new ChatGPTException(Constants.ResponseCode.TOKEN_ERROR.getCode(), Constants.ResponseCode.TOKEN_ERROR.getInfo());
        }

        ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
        emitter.onCompletion(()->{
            log.info("应答未完成，使用模型{}",chatProcess.getModel());
        });
        emitter.onError(throwable -> {log.info("应答出错，使用模型{}",chatProcess.getModel(),throwable);});


        try{
            this.doMessageResponse(chatProcess,emitter);
        } catch (JsonProcessingException e) {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

        return emitter;
    }

    protected abstract void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter responseBodyEmitter) throws JsonProcessingException;
}