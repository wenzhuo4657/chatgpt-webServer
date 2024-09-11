package chat_server.hjs.domain.ChatWeb.model.enity;

import chat_server.hjs.Infrastructure.common.AuthEnum;
import chat_server.hjs.domain.ChatWeb.model.valobj.UserAccountStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @className: UserAccountQuotaEntity
 * @author: wenzhuo4657
 * @date: 2024/9/11 10:49
 * @Version: 1.0
 * @description: 用户状态校验实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountQuotaEntity {

    /**
     * 用户ID
     */
    private String openid;
    /**
     * 总量额度
     */
    private Integer totalQuota;
    /**
     * 剩余额度
     */
    private Integer surplusQuota;
    /**
     * 账户状态
     */
    private UserAccountStatusVO userAccountStatusVO;
    /**
     * 模型类型；一个卡支持多个模型调用，这代表了允许使用的模型范围
     */
    private List<String> allowModelTypeList;

    public  void setAllowModelTypeList(String modelTypes){
        String[] vals=modelTypes.split(AuthEnum.SPLIT);
        this.allowModelTypeList= Arrays.asList(vals);
    }

}