package chat_server.hjs.domain.ChatWeb.model.enity;

import chat_server.hjs.domain.ChatWeb.model.valobj.LogicCheckTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleLogicEntity<T> {

    private LogicCheckTypeVO type;
    private String info;
    private T data;

}