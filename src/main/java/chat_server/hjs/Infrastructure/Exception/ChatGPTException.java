package chat_server.hjs.Infrastructure.Exception;

/**
 * @className: ChatGPTException
 * @author: wenzhuo4657
 * @date: 2024/9/7 9:11
 * @Version: 1.0
 * @description:
 */
public class ChatGPTException extends  RuntimeException{
    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;

    public ChatGPTException(String code) {
        this.code = code;
    }

    public ChatGPTException(String code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public ChatGPTException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ChatGPTException(String code, String message, Throwable cause) {
        this.code = code;
        this.message = message;
        super.initCause(cause);
    }
}