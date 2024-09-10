package chat_server.hjs.domain.ChatWeb.model.valobj;

/**
 * @className: Constants
 * @author: wenzhuo4657
 * @date: 2024/9/8 8:34
 * @Version: 1.0
 * @description:
 */
public class Constants {

    public enum ResponseCode {
        SUCCESS("0000", "成功"),
        UN_ERROR("0001", "未知失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        TOKEN_ERROR("0003", "权限拦截");

        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        ResponseCode() {
        }

        @Override
        public String toString() {
            return "ResponseCode{" +
                    "code='" + code + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }

        public String getCode() {
            return code;
        }

        public ResponseCode setCode(String code) {
            this.code = code;
            return this;
        }

        public String getInfo() {
            return info;
        }

        public ResponseCode setInfo(String info) {
            this.info = info;
            return this;
        }
    }
}