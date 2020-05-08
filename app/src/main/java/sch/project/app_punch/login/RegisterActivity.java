package sch.project.app_punch.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_mail;
    EditText editText_password;
    EditText editText_repassword;
    EditText editText_name;
    EditText editText_school;
    EditText editText_classes;
    TextView login;
    TextView register;
    String mail;
    String name;
    String password;
    String repassword;
    String school;
    String classes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        editText_mail = findViewById(R.id.et_register_mail);
        editText_name = findViewById(R.id.et_register_name);
        editText_password = findViewById(R.id.et_register_password);
        editText_repassword = findViewById(R.id.et_register_repassword);
        editText_school = findViewById(R.id.et_register_school);
        editText_classes = findViewById(R.id.et_register_classes);
        register = findViewById(R.id.tv_register);
        login = findViewById(R.id.tv_change_login);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void getData(){
        name = editText_name.getText().toString().trim();
        mail = editText_mail.getText().toString().trim();
        password = editText_password.getText().toString().trim();
        repassword = editText_repassword.getText().toString().trim();
        school = editText_school.getText().toString().trim();
        classes = editText_classes.getText().toString().trim();

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_register){
            getData();
            if (name.equals("")||mail.equals("")||password.equals("")||repassword.equals("")||school.equals("")||classes.equals("")){
                Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();
            }
            if (!password.equals(repassword.trim()))
                Toast.makeText(this,"密码输入不一致",Toast.LENGTH_SHORT).show();
            else {
                RegisterModel registerModel = new RegisterModel();
                boolean isSuccess = false;
                try {
                   isSuccess =  registerModel.getInfo(name,password,school,mail,classes); //向后端请求注册信息
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess){//如果成功登陆
                    saveUser();

                    /**
                     * 再次登陆获取SessionID
                     */
                    LoginModel loginModel = new LoginModel();
                    try {
                        loginModel.getInfo(name,password);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();

            }


        }
        if (v.getId()==R.id.tv_change_login){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    /**
     * 保存输入框中信息
     */
    void saveUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("school",school);
        editor.putString("classes",classes);
        editor.putString("mail",mail);
        editor.putString("userName",name);
        editor.putString("islogin","true");
        editor.apply();
    }
}
