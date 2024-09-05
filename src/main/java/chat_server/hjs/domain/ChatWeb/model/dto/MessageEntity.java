package chat_server.hjs.domain.ChatWeb.model.dto;

public class MessageEntity {

    private String role;
    private String content;
    private String name;

    @Override
    public String toString() {
        return "MessageEntity{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageEntity() {
    }

    public MessageEntity(String role, String content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
    }
}