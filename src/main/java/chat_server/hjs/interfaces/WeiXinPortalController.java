package chat_server.hjs.interfaces;

import ToOne.chatglm_sdk_master.model.RequestSSE;
import ToOne.chatglm_sdk_master.model.ResponseSync;
import ToOne.chatglm_sdk_master.model.Role;
import ToOne.chatglm_sdk_master.session.Configuration;
import ToOne.chatglm_sdk_master.session.OpenAiSession;
import ToOne.chatglm_sdk_master.session.OpenAiSessionFactory;
import ToOne.chatglm_sdk_master.session.defaults.DefaultOpenAiSessionFactory;
import chat_server.hjs.Infrastructure.common.WeiXinfiled;
import chat_server.hjs.Infrastructure.util.XmlUtil;
import chat_server.hjs.application.IWeiXinValidateService;
import chat_server.hjs.domain.Validate.model.MessageTextEntity;
import com.alibaba.fastjson.JSON;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @className: WeiXinPortalController
 * @author: wenzhuo4657
 * @date: 2024/5/21 18:26
 * @Version: 1.0
 * @description: 微信公众号相关处理
 */

@RestController
@RequestMapping("/wx/portal/{appid}")
public class WeiXinPortalController {

    private final Logger logger = LoggerFactory.getLogger(WeiXinPortalController.class);


    @Value("${wx.config.originalid}")
    private String originalId;


    @Resource
    private IWeiXinValidateService weiXinValidateService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;


    private final OpenAiSession session;//会话链接
    //存放其返回结果   K：Message.getContent
    private final Map<String, String> openAiDataMap = new ConcurrentHashMap<>();


    //    存放处理最终时间，此处要求起处理时间不超过15秒，如果超过则发送默认响应，要求服务端再次请求，
//    且注意，某次请求失败或者结束时，要删除其键
//    k:Message.getContent
    private final Map<String, Long> openAiENDTimeMap = new ConcurrentHashMap<>();


    // 存放OpenAi调用次数数据   : K:Message.getContent
    private final Map<String, Integer> openAiRetryCountMap = new ConcurrentHashMap<>();

    public WeiXinPortalController() {
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("dd48fa165df16a61c7add47c69dcd099.y88gEvsbZcVi65ja");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        this.session = factory.openSession();
        logger.info("开始 openAiSession");
    }

    /**
     * 微信服务器配置验证:微信服务器会发送get请求，进行签名的验证，
     * 如果验证成功则返回echostr,则接入生效，服务器配置生效，否则接入服务器失败，
     * 成功后可根据接口文档进行其他配置<p>
     * * appid     微信端AppID<br>
     * * signature 微信端发来的签名<br>
     * * timestamp 微信端发来的时间戳<br>
     * * nonce     微信端发来的随机字符串<br>
     * * echostr   微信端发来的验证字符串<br>
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String validate(
            @PathVariable String appid,
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "timestamp", required = false) String timestamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestParam(value = "echostr", required = false) String echostr
    ) {
        try {
            logger.info("微信公众号验签信息{}开始 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr);

            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = weiXinValidateService.checkSign(signature, timestamp, nonce);
            logger.info("微信公众号验签信息{}完成 check：{}", appid, check);

            if (!check) {
                return null;
            }

            return echostr;
        } catch (Exception e) {
            logger.error("微信公众号验签信息{}失败 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    /**
     * des:
     * document:https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html
     * 注意：
     * 1,微信服务器将POST消息的XML数据包到开发者填写的URL上，
     * 具体格式官网详见，但此处由于类注解@RestController，将其又变成json格式，也就是参数列表中的requestBody，使用xmlutil将其解析，
     * 2.由于公众号有5秒的超时报错机制，解决方式有异步回复处理,但个人订阅号没有这个功能，
     * 此处采用的是本地存放响应结果，处理过程中发送固定回复，告知订阅者尚在处理中，
     * 也就是是说需要用户不断请求相同的问题，直到处理完成即时返回结果
     *
     *
     * 3，bug:
     * 600byte字节限制，此处未做特殊处理
     */

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) throws InterruptedException {
        logger.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
        MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
        logger.info("请求次数{}", Objects.isNull(openAiRetryCountMap.get(message.getContent().trim())) ? 1 : openAiRetryCountMap.get(message.getContent().trim()));


        String content = message.getContent().trim();//订阅者消息
        Long endTime= openAiENDTimeMap.get(content);//消息处理的起始时间
        String Data = openAiDataMap.get(content);//消息的处理结果，
        Integer RetryCount = openAiRetryCountMap.get(content);//重试次数




        MessageTextEntity res = new MessageTextEntity();
        res.setToUserName(openid);
        res.setFromUserName(originalId);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        if (Objects.isNull(Data) || "NULL".equals(Data)) {
            String data = WeiXinfiled.DefaultRelyText;//默认处理中回复文本
            Integer retryCount = openAiRetryCountMap.get(message.getContent().trim());

            //处于微信服务器端超时重试次数内，可以休眠等待处理，每次五秒等待时间，此处为避免网络通信所导致的延迟，从第一次起，可以等待15秒响应结果，
            if (Objects.isNull(RetryCount)) {//处于第一次请求，未重试
                logger.info("消息处理{}", message.getContent().trim());
                openAiENDTimeMap.put(content, System.currentTimeMillis()+WeiXinfiled.RetryTime);
                if (openAiDataMap.get(content) == null) {
                    doChatGPTTask(content);
                }
                openAiRetryCountMap.put(content,1);
                TimeUnit.SECONDS.sleep(5);

            } else if (retryCount<3) {
                Long sleepTime = (endTime - System.currentTimeMillis())>5?5:(endTime - System.currentTimeMillis());
                logger.info("消息请求：第{}次",retryCount);
                retryCount = retryCount + 1;
                openAiRetryCountMap.put(content, retryCount);
                TimeUnit.SECONDS.sleep(sleepTime);
                if (openAiDataMap.get(message.getContent().trim()) != null && !"NULL".equals(openAiDataMap.get(message.getContent().trim()))) {
                    data = openAiDataMap.get(message.getContent().trim());
                }
            }

            res.setContent(data);
            return XmlUtil.beanToXml(res);
        }

        res.setContent(openAiDataMap.get(content));
        String result = XmlUtil.beanToXml(res);
        logger.info("接收微信公众号信息请求{}完成 {}", openid, result);
        openAiDataMap.remove(content);
        openAiENDTimeMap.remove(content);
        openAiRetryCountMap.remove(content);
        return result;

    }

    private void doChatGPTTask(String content) {
        openAiDataMap.put(content, "NULL");
        taskExecutor.execute( ()->{

            RequestSSE  request=new RequestSSE();
            request.setStream(false);
            request.setMessages(
                    new ArrayList<RequestSSE.Message>(){


                        private static final long serialVersionUID = -7988151926241837899L;
                        {
                            add(RequestSSE.Message.builder()
                                    .role(Role.user.getCode())
                                    .content(content)
                                    .build());
                        }
                    }
            );
            // 请求
            ResponseSync response = null;
            try {
                response = session.completionsSync(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            openAiDataMap.put(content, response.getChoices().get(0).getMessage().getContent());
        });


    }


}