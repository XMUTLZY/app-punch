package sch.project.app_punch.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sch.project.app_punch.MainActivity;
import sch.project.app_punch.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_username;
    EditText editText_password;
    TextView login;
    TextView register;
    String username;
    String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        editText_username = findViewById(R.id.et_user_name);
        editText_password = findViewById(R.id.et_user_pwd);
        login = findViewById(R.id.tv_login);
        login.setOnClickListener(this);
        register = findViewById(R.id.tv_change_register);
        register.setOnClickListener(this);
    }

    private void getData(){

        username = editText_username.getText().toString().trim();
        password = editText_password.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_login){
            getData();
            if (username.equals("")||password.equals("")){
                Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();
            }
            else {
                LoginBean loginBean = null;
                LoginModel loginModel = new LoginModel();
                try {
                    loginBean = loginModel.getInfo(username,password);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (loginBean.getMessage().equals("登录成功")){
                    saveLogin(loginBean);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }

        }
        if (v.getId()==R.id.tv_change_register){
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }

    }

    void saveLogin(LoginBean loginBean){
        /**
         * 登陆成功后
         * 使用SharePreferences写入键值对
         */
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("school",loginBean.getVo().getSchool());
        editor.putString("classes",loginBean.getVo().getClasses());
        editor.putString("mail",loginBean.getVo().getEmail());
        editor.putString("userName",loginBean.getVo().getUserName());
        editor.putString("islogin","true");
        editor.apply();
    }
}
