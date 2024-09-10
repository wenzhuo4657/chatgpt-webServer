package chat_server.hjs.domain.ChatWeb.service.rule.impl;

import chat_server.hjs.domain.ChatWeb.model.dto.ChatProcessAggregate;
import chat_server.hjs.domain.ChatWeb.model.enity.RuleLogicEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: AccessLimitFilter
 * @author: wenzhuo4657
 * @date: 2024/9/10 10:30
 * @Version: 1.0
 * @description: 访问次数过滤，
 * 注意：白名单用户不受该约束
 */
@Component
public class AccessLimitFilter implements ILogicFilter {

    private Logger log= LoggerFactory.getLogger(AccessLimitFilter.class);

    @Value("${app.config.limit-count}")
    private  Integer limitCount;
    @Value("${app.config.white-list}")
    private  String  whitelist;


    @Resource
    private Cache<String, Integer> visitCache;
    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess) throws Exception {

        if (chatProcess.isWhiteList(whitelist)){
                return  RuleLogicEntity.<ChatProcessAggregate>builder()
                        .type(LogicCheckTypeVO.SUCCESS).build();
        }
        String openid= chatProcess.getOpenid();
        Integer integer = visitCache.get(openid, () -> 0);
        if (integer<limitCount){
                visitCache.put(openid,integer+1);
            return  RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS).build();
        }



        return RuleLogicEntity.<ChatProcessAggregate>builder().
                info("您今日的免费" + limitCount + "次，已耗尽！").
                type(LogicCheckTypeVO.REFUSE).build();
    }
}