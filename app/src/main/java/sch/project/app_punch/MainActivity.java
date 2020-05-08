package sch.project.app_punch;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import sch.project.app_punch.ui.punch.PunchFragment;
import sch.project.app_punch.ui.statistics.StatisticsFragment;
import sch.project.app_punch.ui.user.UserFragment;

public class MainActivity extends AppCompatActivity {

    private EasyNavigationBar navigationBar;

    private List<Fragment> fragments = new ArrayList<>();

    private String[] tabText = {"签到", "统计", "我的"};

    //未选中icon
    private int[] normalIcon = {R.drawable.qiandao_unselect, R.drawable.tongji_unselect, R.drawable.wode_unselect};
    //选中时icon
    private int[] selectIcon = {R.drawable.qiandao_select, R.drawable.tongji_select, R.drawable.wode_select};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar = findViewById(R.id.navigationBar);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_add_view, null);
        fragments.add(new PunchFragment());
        fragments.add(new StatisticsFragment());
        fragments.add(new UserFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .normalTextColor(Color.parseColor("#666666"))
                .selectTextColor(Color.parseColor("#aaaaaa"))
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(100)
                .anim(Anim.ZoomIn)
                .canScroll(true)
                .build();
    }

}
