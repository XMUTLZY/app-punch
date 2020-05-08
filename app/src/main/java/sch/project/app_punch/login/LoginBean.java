package sch.project.app_punch.login;

public class LoginBean {


    /**
     * message : 登录成功
     * vo : {"id":1,"userName":"林哈","email":"24142153@qq.com","password":"e10adc3949ba59abbe56e057f20f883e","school":"武汉工商学院","classes":"16计算机与科学技术1班","createTime":"2020-05-03T17:04:18.000+0000","status":1}
     * status_code : 200
     */

    private String message;
    private VoBean vo;
    private int status_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VoBean getVo() {
        return vo;
    }

    public void setVo(VoBean vo) {
        this.vo = vo;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public static class VoBean {
        /**
         * id : 1
         * userName : 林哈
         * email : 24142153@qq.com
         * password : e10adc3949ba59abbe56e057f20f883e
         * school : 武汉工商学院
         * classes : 16计算机与科学技术1班
         * createTime : 2020-05-03T17:04:18.000+0000
         * status : 1
         */

        private int id;
        private String userName;
        private String email;
        private String password;
        private String school;
        private String classes;
        private String createTime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
