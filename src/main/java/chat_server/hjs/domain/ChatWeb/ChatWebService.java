package chat_server.hjs.domain.ChatWeb;

import ToOne.chatglm_sdk_master.session.OpenAiSession;
import chat_server.hjs.Infrastructure.Exception.ChatGPTException;
import chat_server.hjs.Infrastructure.util.ChatglmUtils;
import chat_server.hjs.application.IChatWebService;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @className: ChatWebService
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:16
 * @Version: 1.0
 * @description:
 */
@Service("IChatWebService")
public class ChatWebService implements IChatWebService {

    @Value("${chat.config.pers}")
    public  String pers ;

    protected static OpenAiSession openAiSession= ChatglmUtils.getSession();
    @Override
    public ResponseBodyEmitter completions(ChatProcessAggregate chatProcess) {
        if (!pers.equals(chatProcess.getToken())){
            throw  new ChatGPTException()
        }

        return null;
    }
}