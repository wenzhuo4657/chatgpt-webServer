package chat_server.hjs.domain.ChatWeb.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @className: UserAccountStatusVO
 * @author: wenzhuo4657
 * @date: 2024/9/11 10:53
 * @Version: 1.0
 * @description: 用户状态值对象
 */
@Getter
@AllArgsConstructor
public enum UserAccountStatusVO {

    AVAILABLE(0, "可用"),
    FREEZE(1,"冻结"),
            ;
    private final Integer code;
    private final String info;

      /**
         *  des:
       *  根据状态码返回值对象，如果没有对应状态码默认返回 FREEZE(1,"冻结")
         * */
    public  static  UserAccountStatusVO get(Integer code){
            switch (code){
                case 0:{
                    return UserAccountStatusVO.AVAILABLE;
                }
                case 1:{
                    return UserAccountStatusVO.FREEZE;
                }
                default:return UserAccountStatusVO.FREEZE;
            }
    }



}