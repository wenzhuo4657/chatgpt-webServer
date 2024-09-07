package chat_server.hjs.domain.ChatWeb.model.dto;

import ToOne.chatglm_sdk_master.model.ChatGLMModel;
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
    /** 验证信息 */
    private String token;
    /** 默认模型 */
    private String model = ChatGLMModel.GLM_4.getCode();
    /** 问题描述 */
    private List<MessageEntity> messages;


    @Override
    public String toString() {
        return "ChatProcessAggregate{" +
                "token='" + token + '\'' +
                ", model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public ChatProcessAggregate(String token, String model, List<MessageEntity> messages) {
        this.token = token;
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
        private String token;
        /** 默认模型 */
        private String model = ChatGLMModel.GLM_4.getCode();
        /** 问题描述 */
        private List<MessageEntity> messages;


        public ChatProcessAggregateBuilder setToken(String token) {
            this.token = token;
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
            return new ChatProcessAggregate(this.token,this.model,this.messages);
        }

    }
}