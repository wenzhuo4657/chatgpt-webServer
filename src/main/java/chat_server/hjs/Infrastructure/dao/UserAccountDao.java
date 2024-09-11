package chat_server.hjs.Infrastructure.dao;


import chat_server.hjs.Infrastructure.po.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import retrofit2.http.PartMap;

/**
 * (UserAccount)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-11 11:34:30
 */

@Mapper
public interface UserAccountDao {

    int subAccountQuota(@Param("openid") String openid);

    UserAccount queryUserAccount(@Param("openid") String openid);


  

}

