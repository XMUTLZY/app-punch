package sch.project.app_punch.login;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import sch.project.app_punch.ui.punch.LocationVo;
import sch.project.app_punch.util.OkHttpRequest;

public class LoginModel {
    private static final String ACTIVITY_TAG = "LoginModel";


    public LoginBean getInfo(String username, String password) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new getResponseData(username, password));
        LoginBean responseBean = JSON.parseObject(future.get(), LoginBean.class);
        if (future.isDone()) {

            es.shutdown();
        }

        return responseBean;
    }

    class getResponseData implements Callable<String> {


        String user_name;
        String password;

        public getResponseData(String user_name, String password) {
            this.user_name = user_name;
            this.password = password;
            Log.d("register-json", toString());
        }

        @Override
        public String toString() {
            return "{" +
                    "\"user_name\":\"" + user_name +
                    "\", \"password\":\"" + password +
                    "\"}";
        }

        @Override
        public String call() throws Exception {
            String accessToken = "";
            OkHttpRequest okHttpRequest = new OkHttpRequest();

            String accessTokenUrl = "http://121.199.69.250:8080/api-punch/user/login";
            try {
                //发送请求
                accessToken = okHttpRequest.post(accessTokenUrl, toString());
                Log.d(ACTIVITY_TAG, accessToken);
            } catch (IOException e) {

                e.printStackTrace();
            }

            return accessToken;
        }
    }
}


