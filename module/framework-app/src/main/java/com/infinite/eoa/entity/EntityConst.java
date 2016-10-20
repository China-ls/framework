package com.infinite.eoa.entity;

/**
 * Created by hx on 16-7-27.
 */
public class EntityConst {
    /**
     * Created by hx on 16-7-25.
     */
    public static enum FieldType {
        INT, FLOAT, STRING;
    }

    /**
     * Created by hx on 16-7-25.
     */
    public static enum EntityStatus {
        NORMAL(1), DELETE(9), DISABLE(2);

        public final int val;
        EntityStatus(int v) {
            this.val = v;
        }
    }

    /**
     * Created by hx on 16-7-25.
     */
    public static enum AccountType {
        CLIENT, SYSTEM;
    }

    public static final class EmployeeSex {
        public static final int FEMALE = 1;
        public static final int MALE = 1;
    }

    public static final class SensorOnline {
        public static final int YES = 1;
        public static final int NO = 2;
        public static final int IDLE = 3;
    }

    public static final class EmployeeType {
        /**
         * 巡线
         */
        public static final int LINE_PATROL = 1;
        /**
         * 工程人员
         */
        public static final int ENGINEERING = 2;
        /**
         * 核查人员
         */
        public static final int VERIFICATION = 3;
    }


    public static final class CollectionName {
        //        public static final String DEFAULT = "framework";
        public static final String ACCOUNT = "account";
        public static final String DEPARTMENT = "department";
        public static final String DEPARTMENT_EMPLOYEE = "department_employee";
        public static final String EMPLOYEE = "employee";
        public static final String ROLE = "role";
        public static final String PERMISSION = "permission";
        public static final String EMPLOYEE_DUTY = "employee_duty";
        public static final String APPLICATION = "app";
        public static final String VIRTUALSENSOR = "sensor";
        public static final String VIRTUALSENSOR_DATA = "sensor_data";
        public static final String VIRTUALSENSOR_EVENT = "sensor_event";
        public static final String CENCUS_COMPONENT_DAY_WORK = "cencus_comp_day_work";
//        public static final String COMPONENT = "component";
//        public static final String ACTION = "action";
    }

    public static final class FieldName {
        public static class AccountFN {
            public static final String PREFIX = "account_";
            public static final String ID = PREFIX + "id";
            public static final String NAME = PREFIX + "name";
            public static final String USERNAME = PREFIX + "username";
            public static final String PASSWORD = PREFIX + "password";
            public static final String TOKEN = PREFIX + "token";
            public static final String TOKEN_EXPIRE_TIME = PREFIX + "token_expire_time";
            public static final String STATUS = PREFIX + "status";
            public static final String TYPE = PREFIX + "type";
            public static final String APPLICATIONS = PREFIX + "applications";
            public static final String FIELDS = PREFIX + "fields";

            public static String[] getFields() {
                return new String[]{
                        ID, NAME, USERNAME, PASSWORD, TOKEN, TOKEN_EXPIRE_TIME, STATUS, TYPE/*, APPLICATIONS*/
                };
            }
        }

        public static class ApplicationFN {
            public static final String PREFIX = "app_";
            public static final String ID = PREFIX + "id";
            public static final String NAME = PREFIX + "name";
            public static final String APPKEY = PREFIX + "appkey";
            public static final String STATUS = PREFIX + "status";
            public static final String SENSORS = PREFIX + "sensors";
            public static final String FIELDS = PREFIX + "fields";
        }
    }


}
