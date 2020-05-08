package sch.project.app_punch.ui.statistics;

import java.util.List;

public class StatisticsBean {

    /**
     * vo : [{"id":17,"year":2020,"month":5,"day":5,"user_id":6}]
     * status_code : 200
     */

    private int status_code;
    private List<VoBean> vo;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<VoBean> getVo() {
        return vo;
    }

    public void setVo(List<VoBean> vo) {
        this.vo = vo;
    }

    public static class VoBean {
        /**
         * id : 17
         * year : 2020
         * month : 5
         * day : 5
         * user_id : 6
         */

        private int id;
        private int year;
        private int month;
        private int day;
        private int user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
