package chat_server.hjs.Infrastructure.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * @className: AuthEnum
 * @author: wenzhuo4657
 * @date: 2024/9/8 11:52
 * @Version: 1.0
 * @description:
 */
public class AuthEnum {


    @Value("${wx.config.auth.defaultBase64EncodedSecretKey}")
   public static String defaultBase64EncodedSecretKey;


}