package sch.project.app_punch.ui.punch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cn.iwgang.countdownview.CountdownView;
import sch.project.app_punch.R;
import top.androidman.SuperButton;

public class PunchFragment extends Fragment {
    private PunchViewModel punchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        punchViewModel = ViewModelProviders.of(this).get(PunchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_punch, container, false);
//        final TextView textView = root.findViewById(R.id.text_punch);
//        punchViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //按钮
        SuperButton button = root.findViewById(R.id.punch_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //倒计时
        CountdownView mCvCountdownView = root.findViewById(R.id.cv_countdownView);
        mCvCountdownView.start(995550000); // Millisecond
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {

            }
        });
        return root;
    }
}