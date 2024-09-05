package chat_server.hjs.domain.ChatWeb.model.dto;

import ToOne.chatglm_sdk_master.model.ChatGLMModel;

import java.util.List;

/**
 * @className: ChatGLMResquestDto
 * @author: wenzhuo4657
 * @date: 2024/9/5 17:17
 * @Version: 1.0
 * @description:
 */
public class ChatGLMResquestDto {
    /** 默认模型 */
    private String model = ChatGLMModel.GLM_4.getCode();

    /** 问题描述 */
    private List<MessageEntity> messages;

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

    @Override
    public String toString() {
        return "ChatGLMResquestDto{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }

    public ChatGLMResquestDto() {

    }

    public ChatGLMResquestDto(String model, List<MessageEntity> messages) {
        this.model = model;
        this.messages = messages;
    }
}