package sch.project.app_punch.util;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class OkHttpRequest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public volatile static String JSESSIONID = "0";
    OkHttpClient client = new OkHttpClient();

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();


        return response.body().string();
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (JSESSIONID.equals("0")) {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .addHeader("Cookie", "JSESSIONID=" + JSESSIONID)
                    .url(url)
                    .post(body)
                    .build();
            if (!JSESSIONID.equals("0")) Log.d("请求JSESSIONID:", JSESSIONID);
        }

    /*  Call call = client.newCall(request);
      call.*/


        Response response = client.newCall(request).execute();

        if (JSESSIONID.equals("0")) {
            Headers headers = response.headers();

            try {
                List cookies = headers.values("Set-Cookie");

                String session = (String) cookies.get(0);

                String sessionid = session.substring(0, session.indexOf(";"));

                JSESSIONID = sessionid.substring(11, sessionid.length());

                Log.d("会话JSESSIONID:", JSESSIONID);
            }catch (Exception e){

            }

//        JSESSIONID = sessionid;
        }
        return response.body().string();
    }
}