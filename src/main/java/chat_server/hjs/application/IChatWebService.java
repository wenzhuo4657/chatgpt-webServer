package chat_server.hjs.application;

import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @className: IChatWebService
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:15
 * @Version: 1.0
 * @description: chatweb自动回答api
 */
public interface IChatWebService {
    ResponseBodyEmitter completions(ChatProcessAggregate chatProcess);
}