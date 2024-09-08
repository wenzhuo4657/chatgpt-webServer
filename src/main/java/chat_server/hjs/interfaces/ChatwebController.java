package chat_server.hjs.interfaces;

import chat_server.hjs.application.IChatWebService;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatGLMResquestDto;
import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.MessageEntity;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @className: ChatwebController
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:02
 * @Version: 1.0
 * @description:
 */
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/")
public class ChatwebController {

    private IChatWebService chatWebService;

    public ChatwebController(IChatWebService chatWebService) {
        this.chatWebService = chatWebService;
    }

    /**
     *  des:
   * * 流式问题，Chat 请求接口
   *      * <p>
   *      * curl -X POST \
   *      * http://localhost:8090/api/v1/chat/completions \
   *      * -H 'Content-Type: application/json;charset=utf-8' \
   *      * -H 'Authorization: b8b6' \
   *      * -d '{
   *      * "messages": [
   *      * {
   *      * "content": "写一个java冒泡排序",
   *      * "role": "user"
   *      * }
   *      * ],
   *      * "model": "gpt-3.5-turbo"
   *      * }'
     * */
    @RequestMapping(value = "chat/completions", method = RequestMethod.POST)
    public ResponseBodyEmitter completionsStream(@RequestBody ChatGLMResquestDto request, @RequestHeader("Authorization") String token, HttpServletResponse response) {
        log.info("流式问答请求开始，使用模型：{} 请求信息：{}", request.getModel(), JSON.toJSONString(request.getMessages()));
        try{
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            ChatProcessAggregate aggregate = ChatProcessAggregate.Builder().
                    setToken(token)
                    .setModel(request.getModel())
                    .setMessages(

                            request.getMessages().stream()
                                    .map(entity -> MessageEntity.builder()
                                            .setRole(entity.getRole())
                                            .setContent(entity.getContent())
                                            .setName(entity.getContent())
                                            .build())
                                    .collect(Collectors.toList())
                    ).build();
           return chatWebService.completions(aggregate);
        } catch (Exception e) {
            log.error("流式应答，请求模型：{} 发生异常", request.getModel(), e);
            throw new RuntimeException(e);
        }


    }

}