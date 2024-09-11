package chat_server.hjs.domain.ChatWeb.repository;

import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;

/**
 * @className: IOpenAiRepository
 * @author: wenzhuo4657
 * @date: 2024/9/11 11:18
 * @Version: 1.0
 * @description:
 */
public interface IChatWebRepository {
    int subAccountQuota(String openai);

    UserAccountQuotaEntity queryUserAccount(String openid);

}