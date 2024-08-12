package org.example.smartScoring.smartScoring.model.Scoring;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class chartWithAi {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("properties.javaModel");
    private static String API_KEY = bundle.getString("API_KEY");
    private static String SECRET_KEY = bundle.getString("SECRET_KEY");

    static final OkHttpClient HTTP_CLIENT =
            new OkHttpClient().newBuilder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

    private String massage = "{\"messages\":[";
    private String output = null;

    /**
     * 详细见千帆模型文档
     * @return
     * @throws IOException
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }


    /**
     * 将文本修改成云端大语言模型可识别的json格式
     * @param inputContext 聊天文本
     */
    private void input(String inputContext) {
        if (output != null) {
            String str = ",\n" +
                    "  {\n" +
                    "    \"role\": \"assistant\",\n" +
                    "    \"content\": \"" +
                    output + "\"\n" +
                    "  },";
            massage += str;
        }
        String str = "{\"role\":\"user\"," +
                "\"content\":\"" + inputContext + "\"}";
        massage += str;
    }

    /**
     * 与云端大语言模型对话并获取结果
     * @param inputContext 输入的聊天内容
     * @return 大语言模型的结果
     * @throws IOException
     */
    public String Chart(String inputContext) throws IOException {
        input(inputContext);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, massage + "]}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-speed-128k?access_token="
                        + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String resultJsonBody = response.body().string();
        // 将JSON字符串转换为JSONObject
        JSONObject jsonObject = new JSONObject(resultJsonBody);
        // 提取result字段的值
        String resultText = jsonObject.getString("result");
        output = resultText;
        // 输出提取的结果
        return resultText;
    }
}
