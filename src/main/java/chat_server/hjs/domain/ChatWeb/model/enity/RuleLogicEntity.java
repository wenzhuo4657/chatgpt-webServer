package chat_server.hjs.domain.ChatWeb.model.enity;

import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


  /**
     *  des:
   *  规则校验实体，
   *  type：表示校验的结果
   *  info：表示校验的提示信息
   *  data字段表示校验的数据，
   *
     * */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleLogicEntity<T> {

    private LogicCheckTypeVO type;
    private String info;
    private T data;

}