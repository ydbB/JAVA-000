import okhttp3.*;

import java.io.IOException;

public class OkHttpClient1 {
    private static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws Exception {
        OkHttpClient1 oclient = new OkHttpClient1();
        String json = "";
        String url = "http://localhost:8081/";
        String response = oclient.post(url,json);
        System.out.println(response);
    }
}
