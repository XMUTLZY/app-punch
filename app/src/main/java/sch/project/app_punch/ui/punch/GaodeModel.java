package sch.project.app_punch.ui.punch;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sch.project.app_punch.util.OkHttpRequest;

public class GaodeModel {
    private static final String ACTIVITY_TAG = "DiyListModel";


    public String getInfo(double lat, double lng) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new getResponseData(lat, lng));
        LocationVo responseBean = JSON.parseObject(future.get(),LocationVo.class);
        if (future.isDone()) {

            es.shutdown();
        }
        String data = responseBean.getResult().getFormatted_address();

        return data;
    }

    class getResponseData implements Callable<String> {


        double lat;
        double lng;

        public getResponseData(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public String call() throws Exception {
            String accessToken = "";
            OkHttpRequest okHttpRequest = new OkHttpRequest();

            String accessTokenUrl = "http://api.map.baidu.com/geocoder?location="+lat+","+lng+"&output=json&ak=oTdmc4R89H66zYEm6V3XAYz9BiMfkmCI" ;
            try {
                //发送请求
                accessToken = okHttpRequest.get(accessTokenUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(ACTIVITY_TAG,accessToken);
            return accessToken;
        }
    }
}
