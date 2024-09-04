package chat_server.hjs.Infrastructure.common;

public interface WeiXinfiled {

    Integer textCount =3;//消息文本超时重试次数
    String DefaultRelyText= "消息处理耗时过长，请稍等片刻后重新发送相同的文本";
    Long RetryTime=15*1000L;//毫秒级别
}
