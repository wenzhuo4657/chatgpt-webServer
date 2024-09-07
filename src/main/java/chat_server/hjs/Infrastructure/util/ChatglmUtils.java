package chat_server.hjs.Infrastructure.util;

import ToOne.chatglm_sdk_master.model.RequestSSE;
import ToOne.chatglm_sdk_master.model.ResponseStream;
import ToOne.chatglm_sdk_master.model.Role;
import ToOne.chatglm_sdk_master.session.Configuration;
import ToOne.chatglm_sdk_master.session.OpenAiSession;
import ToOne.chatglm_sdk_master.session.OpenAiSessionFactory;
import ToOne.chatglm_sdk_master.session.defaults.DefaultOpenAiSessionFactory;
import com.alibaba.fastjson.JSON;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @className: ChatglmUtils
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:27
 * @Version: 1.0
 * @description:
 */
public class ChatglmUtils {
    private static OpenAiSession session=null;
    private static Logger log= LoggerFactory.getLogger(ChatglmUtils.class);

    public  static OpenAiSession getSession(){
        if (!Objects.isNull(session)){
            return session;
        }
        Configuration configuration=new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("dd48fa165df16a61c7add47c69dcd099.y88gEvsbZcVi65ja");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);

        OpenAiSessionFactory factory=new DefaultOpenAiSessionFactory(configuration);
        session=factory.openSession();
        return session;
    }

    public  static ResponseStream  SSE_stream(HttpServletResponse res){
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            RequestSSE request = new RequestSSE();
            request.setStream(true);
            request.setMessages(new ArrayList<RequestSSE.Message>(){

                private static final long serialVersionUID = -7988151926241837892L;
                {
                    add(RequestSSE.Message.builder()
                            .role(Role.user.getCode())
                            .content("jdk中存在哪些编译器")
                            .build());
                }

            });

            session.completionsStream(request, new EventSourceListener() {
                @Override
                public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                    if ("[DONE]".equals(data)) {
                        log.info("[输出结束] Tokens {}", JSON.toJSONString(data));
                        return;
                    }

                    ResponseStream response = JSON.parseObject(data, ResponseStream.class);
                    log.info("测试结果：{}", JSON.toJSONString(response));
                }

                @Override
                public void onClosed(EventSource eventSource) {
                    log.info("对话完成");
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                    log.error("对话失败", t);
                    countDownLatch.countDown();
                }
            });

            // 等待
            countDownLatch.await();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}