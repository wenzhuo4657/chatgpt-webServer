package chat_server.hjs.domain.ChatWeb.service.rule.factory;

import chat_server.hjs.domain.ChatWeb.annotation.LogicStrategy;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.service.rule.ILogicFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public DefaultLogicFactory(List<ILogicFilter<UserAccountQuotaEntity>> logicFilterMap) {
       logicFilterMap.stream()
               .forEach( logic->{
                   LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
                   if (!Objects.isNull(strategy)){
                        this.logicFilterMap.put(strategy.logicModel().code,logic);
                   }

               });
    }

    public Map<String, ILogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public enum LogicModel {

        ACCESS_LIMIT("ACCESS_LIMIT", "访问次数过滤"),
        SENSITIVE_WORD("SENSITIVE_WORD", "敏感词过滤"),
        USER_QUOTA("USER_QUOTA", "用户额度过滤"),
        MODEL_TYPE("MODEL_TYPE", "模型可用范围过滤"),
        ACCOUNT_STATUS("ACCOUNT_STATUS", "账户状态过滤"),
        ;

        private String code;
        private String info;

        LogicModel(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

}