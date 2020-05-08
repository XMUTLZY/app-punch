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

public class RegisterModel {
    private static final String ACTIVITY_TAG = "RegisterModel";


    public boolean getInfo(String username,String password,String school,String mail,String classes) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new getResponseData(username,password,mail,school,classes));

        if (future.isDone()) {
            es.shutdown();
        }

        if (future.get().equals("{\"status_code\":200}")){
            return true;
        }
        else return false;
    }

    class getResponseData implements Callable<String> {


        String user_name;
        String password;
        String email;
        String school;
        String classes;

        public getResponseData(String user_name, String password, String email, String school, String classes) {
            this.user_name = user_name;
            this.password = password;
            this.email = email;
            this.school = school;
            this.classes = classes;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"user_name\":\"" + user_name +
                    "\", \"password\":\"" + password +
                    "\", \"email\":\"" + email +
                    "\", \"school\":\"" + school +
                    "\", \"classes\":\"" + classes +
                    "\"}";
        }

        @Override
        public String call() throws Exception {
            String accessToken = "";
            OkHttpRequest okHttpRequest = new OkHttpRequest();

            String accessTokenUrl = "http://121.199.69.250:8080/api-punch/user/registry" ;
            try {
                //发送请求
                accessToken = okHttpRequest.post(accessTokenUrl,toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(ACTIVITY_TAG,accessToken);
            return accessToken;
        }
    }
    class Status{
        int status_code;

        public int getStatus_code() {
            return status_code;
        }

        public void setStatus_code(int status_code) {
            this.status_code = status_code;
        }
    }
}



