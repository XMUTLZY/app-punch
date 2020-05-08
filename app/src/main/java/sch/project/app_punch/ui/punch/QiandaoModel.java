package sch.project.app_punch.ui.punch;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sch.project.app_punch.login.RegisterModel;
import sch.project.app_punch.util.OkHttpRequest;

public class QiandaoModel {
    private static final String ACTIVITY_TAG = "QiandaoModel";


    public boolean getInfo() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new getResponseData());

        if (future.isDone()) {
            es.shutdown();
        }

        if (future.get().equals("{\"status_code\":200}")){
            return true;
        }
        else return false;
    }

    class getResponseData implements Callable<String> {


        @Override
        public String call() throws Exception {
            String accessToken = "";
            OkHttpRequest okHttpRequest = new OkHttpRequest();

            String accessTokenUrl = "http://121.199.69.250:8080/api-punch/punch/sign-in" ;
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
}
