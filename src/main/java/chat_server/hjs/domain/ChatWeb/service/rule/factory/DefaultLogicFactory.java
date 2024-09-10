package chat_server.hjs.domain.ChatWeb.service.rule.factory;

import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @className: DefaultLogicFactory
 * @author: wenzhuo4657
 * @date: 2024/9/10 12:04
 * @Version: 1.0
 * @description: 规则工厂
 * 使用composite策略
 */
@Service
public class DefaultLogicFactory {

    private Map<String, ILogicFilter> logicFilterMap=new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter> logicFilterMap) {
       logicFilterMap.stream()
               .forEach( logic->{

               });
    }
}