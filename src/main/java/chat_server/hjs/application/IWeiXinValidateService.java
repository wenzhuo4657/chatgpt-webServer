package chat_server.hjs.application;

/**
 * @className: IWeiXinValidateService
 * @author: wenzhuo4657
 * @date: 2024/5/21 18:24
 * @Version: 1.0
 * @description:  微信公众号验签api
 */
public interface IWeiXinValidateService {

  /**
     *  des: 验证签名：
     * */
    boolean checkSign(String signature, String timestamp, String nonce);
}