package chat_server.hjs.domain.ChatWeb.model.dto;

import ToOne.chatglm_sdk_master.model.ChatGLMModel;
import chat_server.hjs.Infrastructure.common.AuthEnum;
import chat_server.hjs.domain.ChatWeb.model.enity.MessageEntity;

import java.util.List;

/**
 * @className: ChatProcessAggregate
 * @author: wenzhuo4657
 * @date: 2024/9/7 8:14
 * @Version: 1.0
 * @description:
 */
public class ChatProcessAggregate {
    /** 用户id */
    private String openid;
    /** 默认模型 */
    private String model = ChatGLMModel.GLM_4.getCode();
    /** 问题描述 */
    private List<MessageEntity> messages;


    public boolean isWhiteList(String whiteListStr){
        String[] split = whiteListStr.split(AuthEnum.SPLIT);
        for (String id:split){
            if (id.equals(openid)){
                return true;
            }
        }
        return  false;
    }


    @Override
    public String toString() {
        return "ChatProcessAggregate{" +
                "openid='" + openid + '\'' +
                ", model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public ChatProcessAggregate() {
    }

    public ChatProcessAggregate(String openid, String model, List<MessageEntity> messages) {
        this.openid = openid;
        this.model = model;
        this.messages = messages;
    }

    public  static  ChatProcessAggregateBuilder Builder(){
        return  new ChatProcessAggregateBuilder();
    }

    public  static  class ChatProcessAggregateBuilder{
        public ChatProcessAggregateBuilder() {
        }

        /** 验证信息 */
        private String openid;
        /** 默认模型 */
        private String model = ChatGLMModel.GLM_4.getCode();
        /** 问题描述 */
        private List<MessageEntity> messages;


        public ChatProcessAggregateBuilder setOpenid(String openid) {
            this.openid = openid;
            return this;
        }

        public ChatProcessAggregateBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public ChatProcessAggregateBuilder setMessages(List<MessageEntity> messages) {
            this.messages = messages;
            return  this;
        }

        public  ChatProcessAggregate build(){
            return new ChatProcessAggregate(this.openid,this.model,this.messages);
        }

    }
}