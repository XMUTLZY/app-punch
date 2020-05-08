package sch.project.app_punch.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import sch.project.app_punch.MainActivity;
import sch.project.app_punch.R;
import sch.project.app_punch.util.OkHttpRequest;

public class UserFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView username;
    TextView mail;
    TextView school;
    TextView classes;
    TextView tuichu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initData();
        return view;
    }

    void initView(){
        username = view.findViewById(R.id.tv_user_name);
        mail = view.findViewById(R.id.tv_user_mail);
        school = view.findViewById(R.id.tv_user_school);
        classes = view.findViewById(R.id.tv_user_class);
//        tuichu = view.findViewById(R.id.tv_tuichu);
//        tuichu.setOnClickListener(this);
    }
    void initData(){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("userName",""));
        mail.setText(sharedPreferences.getString("mail",""));
        school.setText(sharedPreferences.getString("school",""));
        classes.setText(sharedPreferences.getString("classes",""));
    }

    @Override
    public void onClick(View v) {
//        if (v.getId()==R.id.tv_tuichu){
//            OkHttpRequest.JSESSIONID="0";
//
//        }
    }
}