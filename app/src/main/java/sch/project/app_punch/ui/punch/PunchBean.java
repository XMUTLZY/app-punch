package sch.project.app_punch.ui.punch;

public class PunchBean {


    private String message;
    private String classes;
    private String week;
    private String address;
    private int clock;
    private int status_code;
    private String start_time;
    private String end_time;
    private String now_date;
    private double point_x;
    private double point_y;
    private boolean is_punched;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getNow_date() {
        return now_date;
    }

    public void setNow_date(String now_date) {
        this.now_date = now_date;
    }

    public double getPoint_x() {
        return point_x;
    }

    public void setPoint_x(double point_x) {
        this.point_x = point_x;
    }

    public double getPoint_y() {
        return point_y;
    }

    public void setPoint_y(double point_y) {
        this.point_y = point_y;
    }

    public boolean isIs_punched() {
        return is_punched;
    }

    public void setIs_punched(boolean is_punched) {
        this.is_punched = is_punched;
    }
}
