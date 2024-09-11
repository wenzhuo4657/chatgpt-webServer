package chat_server.hjs.Infrastructure.repository;

import chat_server.hjs.Infrastructure.dao.UserAccountDao;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.repository.IChatWebRepository;

/**
 * @className: ChatWebRepository
 * @author: wenzhuo4657
 * @date: 2024/9/11 11:20
 * @Version: 1.0
 * @description:
 */
public class ChatWebRepository implements IChatWebRepository {

    private UserAccountDao userAccountDao;

    public ChatWebRepository(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public int subAccountQuota(String openai) {


        return 0;
    }

    @Override
    public UserAccountQuotaEntity queryUserAccount(String openid) {
        return null;
    }
}