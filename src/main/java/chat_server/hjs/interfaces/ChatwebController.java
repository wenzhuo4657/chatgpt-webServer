package chat_server.hjs.interfaces;

import chat_server.hjs.domain.ChatWeb.model.dto.ChatGLMResquestDto;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletResponse;

/**
 * @className: ChatwebController
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:02
 * @Version: 1.0
 * @description:
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/")
public class ChatwebController {


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

    }

}