package chat_server.hjs.domain.auth.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: AuthStateEntity
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:28
 * @Version: 1.0
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthStateEntity {

    private String code;
    private String info;
    private String openId;
    private String token;

}
