package sch.project.app_punch.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import sch.project.app_punch.MainActivity;
import sch.project.app_punch.R;

public class LunchActivity extends AppCompatActivity {

    String islogin = "false";
    private static final int WHAT_DELAY = 0x11;// 启动页的延时跳转
    private static final int DELAY_TIME = 3000;// 延时时间
    // 创建Handler对象，处理接收的消息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DELAY:// 延时3秒跳转
                    if (islogin.trim().equals("true")){
                        autoLogin();
                    }
                    else
                        gotoLogin();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lunch);
        // 调用handler的sendEmptyMessageDelayed方法
        handler.sendEmptyMessageDelayed(WHAT_DELAY, DELAY_TIME);
        SharedPreferences sharedPreferences= getSharedPreferences("user", Context.MODE_PRIVATE);
        islogin =sharedPreferences.getString("islogin","");
    }
    void gotoLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    void autoLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",Context.MODE_PRIVATE);
        LoginModel loginModel = new LoginModel();
        try {
            loginModel.getInfo(sharedPreferences.getString("userName",""),sharedPreferences.getString("password",""));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
