package sch.project.app_punch.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sch.project.app_punch.R;

public class StatisticsFragment extends Fragment {

    MaterialCalendarView calendarView;
    StatisticsBean statisticsBean;
    List<StatisticsBean.VoBean> voBeanList;
    boolean isLoad = false;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        isLoad = true;
        initData();
        initView();
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLoad){
            initData();
            initView();
        }

    }


    private void initView() {
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setWeekDayLabels(new String[]{"日", "一", "二", "三", "四", "五", "六"});
        try{
            for(int i=0;i<voBeanList.size();i++){
                calendarView.setSelectedDate(CalendarDay.from(voBeanList.get(i).getYear(), voBeanList.get(i).getMonth(), voBeanList.get(i).getDay()));
            }
        }catch (Exception e){

        }


    }
    private void initData(){
        StatisticsModel statisticsModel = new StatisticsModel();
        try {
            statisticsBean = statisticsModel.getInfo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        voBeanList = statisticsBean.getVo();
    }
}