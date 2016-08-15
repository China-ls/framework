package com.infinite.water.entity;

import java.util.List;

/**
 * Created by xinhong on 16/8/14.
 */
public class VirtualSensor {

    /**
     * app_id : aidiman/xgsn/yinfantech
     * components : [{"actions":[{"channel_id":"%2%","operation":"take_photo","param":"%3%","sensor_id":"%1%"}],"fieldDefinitions":[{"name":"image","type":"image_data"},{"name":"time","type":"isodate"}],"type":"camera_controller","name":"摄像机","id":"1"},{"type":"electricmeter_sensor","name":"电表","fieldDefinitions":[{"name":"power","type":"float","unit":"kWH"},{"name":"voltageA","type":"float","unit":"V"},{"name":"voltageB","type":"float","unit":"V"},{"name":"voltageC","type":"float","unit":"V"},{"name":"currentA","type":"float","unit":"A"},{"name":"currentB","type":"float","unit":"A"},{"name":"currentC","type":"float","unit":"A"},{"name":"time","type":"isodate"}],"id":"2"},{"type":"flowmeter_sensor","name":"流量计","fieldDefinitions":[{"name":"instant","type":"float","unit":"m3/s"},{"name":"positive_total","type":"float","unit":"m3"},{"name":"negtive_totoal","type":"float","unit":"m3"},{"name":"time","type":"isodate"}],"id":"3"},{"fieldDefinitions":[{"name":"card_id","type":"int"},{"name":"time","type":"isodate"}],"type":"cardreader_sensor","name":"读卡器","id":"4"},{"type":"onboard_controller","name":"扬升泵","id":"5:1","actions":[{"channel_id":"%2%","operation":"switch","param":"%3%","sensor_id":"%1%"}],"fieldDefinitions":[{"name":"status","type":"boolean"},{"name":"time","type":"isodate"}],"ref_channel":"6:1"},{"type":"status_sensor","name":"扬升泵状态","id":"6:1","fieldDefinitions":[{"name":"status","type":"boolean"},{"name":"time","type":"isodate"}]}]
     * desc : 浙江省嘉兴市嘉善县魏塘工业园区振华路58号 测试设备1号
     * idle_report : 600
     * internal_id : 2100122001222
     * lat : 30.28
     * lon : 120.12
     * name : 魏塘工业园1号
     * offline_report : 300
     * uuid : 23423da9-b682-4566-8794-9169f484be5e
     */

    private String app_id;
    private String desc;
    private int idle_report;
    private long internal_id;
    private double lat;
    private double lon;
    private String name;
    private int offline_report;
    private String uuid;
    /**
     * actions : [{"channel_id":"%2%","operation":"take_photo","param":"%3%","sensor_id":"%1%"}]
     * fieldDefinitions : [{"name":"image","type":"image_data"},{"name":"time","type":"isodate"}]
     * type : camera_controller
     * name : 摄像机
     * id : 1
     */

    private List<Components> components;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIdle_report() {
        return idle_report;
    }

    public void setIdle_report(int idle_report) {
        this.idle_report = idle_report;
    }

    public long getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(long internal_id) {
        this.internal_id = internal_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffline_report() {
        return offline_report;
    }

    public void setOffline_report(int offline_report) {
        this.offline_report = offline_report;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Components> getComponents() {
        return components;
    }

    public void setComponents(List<Components> components) {
        this.components = components;
    }

    public static class Components {
        private String type;
        private String name;
        private String id;
        /**
         * channel_id : %2%
         * operation : take_photo
         * param : %3%
         * sensor_id : %1%
         */

        private List<Actions> actions;
        /**
         * name : image
         * type : image_data
         */

        private List<FieldDefinitions> fieldDefinitions;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Actions> getActions() {
            return actions;
        }

        public void setActions(List<Actions> actions) {
            this.actions = actions;
        }

        public List<FieldDefinitions> getFieldDefinitions() {
            return fieldDefinitions;
        }

        public void setFieldDefinitions(List<FieldDefinitions> fieldDefinitions) {
            this.fieldDefinitions = fieldDefinitions;
        }

        public static class Actions {
            private String channel_id;
            private String operation;
            private String param;
            private String sensor_id;

            public String getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(String channel_id) {
                this.channel_id = channel_id;
            }

            public String getOperation() {
                return operation;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getSensor_id() {
                return sensor_id;
            }

            public void setSensor_id(String sensor_id) {
                this.sensor_id = sensor_id;
            }
        }

        public static class FieldDefinitions {
            private String name;
            private String type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
