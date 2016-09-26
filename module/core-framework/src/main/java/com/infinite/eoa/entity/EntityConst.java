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
        NORMAL, DELETE, DISABLE;
    }

    /**
     * Created by hx on 16-7-25.
     */
    public static enum AccountType {
        CLIENT, SYSTEM;
    }


    public static final class CollectionName {
//        public static final String DEFAULT = "framework";
        public static final String ACCOUNT = "account";
        public static final String APPLICATION = "app";
        public static final String VIRTUALSENSOR = "sensor";
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

            public static String[] getFields() {
                return new String[]{
                        ID, NAME, APPKEY, STATUS, SENSORS
                };
            }
        }

        public static class VirtualSensorFN {
            public static final String PREFIX = "vs_";
            public static final String ID = PREFIX + "id";
            public static final String NAME = PREFIX + "name";
            public static final String STATUS = PREFIX + "status";
            public static final String COMPONENTS = PREFIX + "components";
            public static final String DATA = PREFIX + "mq_data";
            public static final String CONTROL = PREFIX + "mq_control";
            public static final String SYSTEM = PREFIX + "mq_system";

            public static String[] getFields() {
                return new String[]{
                        ID, NAME, STATUS, COMPONENTS, DATA, CONTROL, SYSTEM
                };
            }
        }

        public static class ComponentFN {
            public static final String PREFIX = "comp_";
            public static final String ID = PREFIX + "id";
            public static final String NAME = PREFIX + "name";
            public static final String STATUS = PREFIX + "status";
            public static final String TYPE = PREFIX + "type";
            public static final String ACTIONS = PREFIX + "actions";
        }
        public static class ActionFN {
            public static final String PREFIX = "action_";
            public static final String NAME = PREFIX + "name";
            public static final String STATUS = PREFIX + "status";
            public static final String ARGUMENTS = PREFIX + "arguments";
        }
    }


}
