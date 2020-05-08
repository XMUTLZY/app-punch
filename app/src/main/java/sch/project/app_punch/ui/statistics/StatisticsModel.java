package sch.project.app_punch.ui.statistics;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sch.project.app_punch.login.LoginBean;
import sch.project.app_punch.login.LoginModel;
import sch.project.app_punch.util.OkHttpRequest;

public class StatisticsModel {
    private static final String ACTIVITY_TAG = "StatisticsModel";


    public StatisticsBean getInfo() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new getResponseData());
        StatisticsBean responseBean = JSON.parseObject(future.get(), StatisticsBean.class);
        if (future.isDone()) {

            es.shutdown();
        }

        return responseBean;
    }

    class getResponseData implements Callable<String> {


        @Override
        public String call() throws Exception {
            String accessToken = "";
            OkHttpRequest okHttpRequest = new OkHttpRequest();

            String accessTokenUrl = "http://121.199.69.250:8080/api-punch/statistics/info";
            try {
                //发送请求
                accessToken = okHttpRequest.post(accessTokenUrl, toString());

            } catch (IOException e) {

                e.printStackTrace();
            }
            Log.d(ACTIVITY_TAG, accessToken);
            return accessToken;
        }
    }
}
