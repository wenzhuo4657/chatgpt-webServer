package hjs;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ChatCompletionExample {

    public static void main(String[] args) throws Exception {
        // 你的API密钥
        String apiKey = "dd48fa165df16a61c7add47c69dcd099.y88gEvsbZcVi65ja";
        // 创建HttpClient实例
        HttpClient client = HttpClient.newHttpClient();
        // 构建请求体
        String requestBody = "{\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"你好\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // 创建HttpRequest实例
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://open.bigmodel.cn/api/paas/v4/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestBody))
                .build();
        // 发送请求并处理响应
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // 输出响应结果
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }



}