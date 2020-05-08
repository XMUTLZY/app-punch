package sch.project.app_punch.ui.punch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.spark.submitbutton.SubmitButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cn.iwgang.countdownview.CountdownView;
import sch.project.app_punch.R;

public class PunchFragment extends Fragment implements View.OnClickListener, AMapLocationListener {

    private View view;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    volatile boolean isRetuen = false;
    private CountdownView mCvCountdownView;

    TextView submitButton;
    TextView location;
    TextView school;
    TextView classes;
    TextView nowday; //今日日期
    TextView round;//签到范围
    TextView time; //签到时间范围
    private ImageView reload;
    private ProgressDialog progDialog = null;// 搜索时进度条
    //用户所在地理位置
    double weidu;
    double jingdu;
    PunchBean punchBean;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_punch, container, false);
        initView();
        initData();
        initPage();
        return view;
    }

    private void initView(){

        reload = view.findViewById(R.id.iv_punch_reload);
        location = view.findViewById(R.id.tv_punch_location);
        school = view.findViewById(R.id.tv_punch_school);
        classes = view.findViewById(R.id.tv_punch_classes);
        submitButton = view.findViewById(R.id.sb_punch_submit);
        nowday = view.findViewById(R.id.tv_punch_now);
        round = view.findViewById(R.id.tv_punch_round);
        time = view.findViewById(R.id.tv_punch_time);
        submitButton.setOnClickListener(this);
        reload.setOnClickListener(this);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        school.setText(sharedPreferences.getString("school",""));
        classes.setText(sharedPreferences.getString("classes",""));

    }

    private void initData(){
        PunchModel punchModel = new PunchModel();
        try {
            punchBean = punchModel.getInfo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initPage(){
        nowday.setText(getToday()+" "+getWeekOfDate(new Date()));

        if (punchBean.getStatus_code()==500){
            //如果没有签到计划
            round.setText("签到范围: 无");
            time.setText(" ");
            location.setText("暂无签到计划");
            reload.setClickable(false);
            submitButton.setEnabled(false);
            submitButton.setClickable(false);
            submitButton.setBackgroundColor(Color.parseColor("#3E3B3B"));
        }
        //有签到计划
        else {
            reload.setClickable(true);
            submitButton.setEnabled(false);
            submitButton.setClickable(false);
            round.setText("签到范围: "+punchBean.getAddress());
            time.setText(punchBean.getStart_time()+"  至  "+punchBean.getEnd_time());
            location.setText("请获取你的地址 ->");
            submitButton.setBackgroundColor(Color.parseColor("#3E3B3B"));
            if (punchBean.isIs_punched()){ //如果已经签到，设置获取地址按键不可按
                submitButton.setText("已签到");
                reload.setClickable(false);
                reload.setClickable(false);

            }
            else { //有签到计划但未签到
                setClock(); //设置倒计时
            }

        }

    }
    void setClock(){
        long endtime = punchBean.getClock()*60000;
        long starttime = 24*60*60000 - getSeconds();
        mCvCountdownView = (CountdownView)view.findViewById(R.id.cv_count);
        mCvCountdownView.start(endtime-starttime); // Millisecond
    }

    /**
     * 签到时判断是否超时
     * @return
     */
    boolean checkClock(){
        long endtime = punchBean.getClock()*60000;
        long starttime = 24*60*60000 - getSeconds();
        if (starttime>endtime)
            return true;
        else
            return false;
    }
    /**
     * 距离明天多少毫秒
     * @return
     */
    private long getSeconds(){
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (long)(tommorowDate.getTimeInMillis()- curDate .getTimeInMillis() ) ;
    }
    private void initGaode(){
        mlocationClient = new AMapLocationClient(this.getContext());
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();

    }

    void checkDistence(){
        if (checkClock()){
            Toast.makeText(this.getContext(), "签到超时", Toast.LENGTH_SHORT).show();
            return;
        }
        if (weidu!=0&&jingdu!=0){
            LatLng mubiao = new LatLng(punchBean.getPoint_y(),punchBean.getPoint_x());
            LatLng yonghu = new LatLng(weidu,jingdu);
            float distance = AMapUtils.calculateLineDistance(mubiao,yonghu);
            if ((int)distance<1000)
                Toast.makeText(this.getContext(), "距离"+(int)distance+"m", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.getContext(), "距离"+(int)distance/1000+"km", Toast.LENGTH_SHORT).show();
            if ((int)distance<10000){
                Toast.makeText(this.getContext(),"签到成功",Toast.LENGTH_LONG).show();
                QiandaoModel qiandaoModel = new QiandaoModel();
                boolean isSuccess = false;
                try {
                   isSuccess =  qiandaoModel.getInfo();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess){
                    submitButton.setClickable(false);
                    submitButton.setText("已签到");
                }
                else {
                    Toast.makeText(this.getContext(),"签到失败请重新签到",Toast.LENGTH_LONG).show();
                }

            }
            else
                Toast.makeText(this.getContext(),"签到失败",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this.getContext(), "请先获取定位", Toast.LENGTH_LONG).show();
    }

    String getToday(){
        String strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        return strNow;
    }

    public  String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_punch_reload:
                showProgressDialog();
                initGaode();
                submitButton.setClickable(true);
                submitButton.setEnabled(true);
                submitButton.setBackgroundColor(Color.parseColor("#ffB419"));
                break;
            case R.id.sb_punch_submit:
                checkDistence();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                weidu = aMapLocation.getLatitude();//获取纬度
                jingdu = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
//                Log.d("本次定位", "" + weidu + jingdu);
                GaodeModel gaodeModel = new GaodeModel();
                String locationStr = null;
                try {
                   locationStr = gaodeModel.getInfo(weidu,jingdu);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                location.setText(locationStr);
                if (progDialog != null) {
                    progDialog.dismiss();
                }
                mlocationClient.onDestroy();
            } else {

            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
            mlocationClient = null;
        }

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this.getContext());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索定位...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
}