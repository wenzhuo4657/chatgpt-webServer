package chat_server.hjs.Infrastructure.repository;

import chat_server.hjs.Infrastructure.dao.UserAccountDao;
import chat_server.hjs.Infrastructure.po.UserAccount;
import chat_server.hjs.domain.ChatWeb.model.enity.UserAccountQuotaEntity;
import chat_server.hjs.domain.ChatWeb.model.valobj.UserAccountStatusVO;
import chat_server.hjs.domain.ChatWeb.repository.IChatWebRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @className: ChatWebRepository
 * @author: wenzhuo4657
 * @date: 2024/9/11 11:20
 * @Version: 1.0
 * @description:
 */
@Repository
public class ChatWebRepository implements IChatWebRepository {

    private UserAccountDao userAccountDao;

    public ChatWebRepository(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public int subAccountQuota(String openai) {
        return userAccountDao.subAccountQuota(openai);
    }

    @Override
    public UserAccountQuotaEntity queryUserAccount(String openid) {
        UserAccount userAccount = userAccountDao.queryUserAccount(openid);
        if (Objects.isNull(userAccount)) {
            return null;
        }
        UserAccountQuotaEntity entity=new UserAccountQuotaEntity();
        entity.setOpenid(userAccount.getOpenid());
        entity.setSurplusQuota(userAccount.getSurplusQuota());
        entity.setTotalQuota(userAccount.getTotalQuota());
        entity.setAllowModelTypeList(userAccount.getModelTypes());
        entity.setUserAccountStatusVO(UserAccountStatusVO.get(userAccount.getStatus()));
        return entity;
    }
}