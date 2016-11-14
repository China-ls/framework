package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceStatusAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceTypeAggregation;
import com.infinite.eoa.entity.aggregation.VsFieldMinMaxAggregation;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import com.infinite.eoa.service.CencusService;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class CencusServiceImpl implements CencusService {

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;
    @Autowired
    private VirtualSensorDataDAO virtualSensorDataDAO;

    @Override
    public ArrayList<VsDeviceTypeAggregation> deviceTypeDistribution() {
        Datastore datastore = virtualSensorDAO.getDatastore();
        AggregationPipeline pipeline = datastore.createAggregation(VirtualSensor.class)
                .group(
                        Group.id(Group.grouping("station_type")),
                        Group.grouping("count", new Accumulator("$sum", 1))
                );
        ArrayList<VsDeviceTypeAggregation> vsDeviceTypeAggregations = new ArrayList<VsDeviceTypeAggregation>();
        Iterator<VsDeviceTypeAggregation> iterator = pipeline.aggregate(VsDeviceTypeAggregation.class);
        while (iterator.hasNext()) {
            vsDeviceTypeAggregations.add(iterator.next());
        }
        return vsDeviceTypeAggregations;
    }

    @Override
    public ArrayList<VsDeviceStatusAggregation> deviceOnlineDistribution() {
        Datastore datastore = virtualSensorDAO.getDatastore();
        AggregationPipeline pipeline = datastore.createAggregation(VirtualSensor.class)
                .group(
                        Group.id(Group.grouping("online")),
                        Group.grouping("count", new Accumulator("$sum", 1))
                );
        ArrayList<VsDeviceStatusAggregation> vsDeviceTypeAggregations = new ArrayList<VsDeviceStatusAggregation>();
        Iterator<VsDeviceStatusAggregation> iterator = pipeline.aggregate(VsDeviceStatusAggregation.class);
        while (iterator.hasNext()) {
            vsDeviceTypeAggregations.add(iterator.next());
        }
        return vsDeviceTypeAggregations;
    }

    @Override
    public HashMap<String, Double> cencusPositiveTotalByStation_type(String station_type, long start, long end) {
        if (start <= 0 || end <= 0 || end <= start) {
            return null;
        }
        Datastore datastore = virtualSensorDAO.getDatastore();

        AggregationPipeline pipeline = datastore.createAggregation(VirtualSensorData.class)
                .match(datastore.createQuery(
                        VirtualSensorData.class).filter("comp_type", "flowmeter_sensor")
                        .field("time").greaterThan(new Date(start))
                        .field("time").lessThan(new Date(end))
                )
                .group(
                        Group.id(Group.grouping("sensor_id")),
                        Group.grouping("max", Group.max("positive_total")),
                        Group.grouping("min", Group.min("positive_total"))
                );
        Iterator<VsFieldMinMaxAggregation> iterator = pipeline.aggregate(VsFieldMinMaxAggregation.class);
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        while (iterator.hasNext()) {
            VsFieldMinMaxAggregation vsFieldMinMaxAggregation = iterator.next();
            vsFieldMinMaxAggregation.convertId();
            hashMap.put(vsFieldMinMaxAggregation.getId(), vsFieldMinMaxAggregation.minus());
        }
        return hashMap;
    }

    @Override
    public HashMap<String, Double> cencusPowerByStation_type(String station_type, long start, long end) {
        if (start <= 0 || end <= 0 || end <= start) {
            return null;
        }
        Datastore datastore = virtualSensorDAO.getDatastore();

        AggregationPipeline pipeline = datastore.createAggregation(VirtualSensorData.class)
                .match(datastore.createQuery(
                        VirtualSensorData.class).filter("comp_type", "electricmeter_sensor")
                        .field("time").greaterThan(new Date(start))
                        .field("time").lessThan(new Date(end))
                )
                .group(
                        Group.id(Group.grouping("sensor_id")),
                        Group.grouping("max", Group.max("power")),
                        Group.grouping("min", Group.min("power"))
                );
        Iterator<VsFieldMinMaxAggregation> iterator = pipeline.aggregate(VsFieldMinMaxAggregation.class);
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        while (iterator.hasNext()) {
            VsFieldMinMaxAggregation vsFieldMinMaxAggregation = iterator.next();
            vsFieldMinMaxAggregation.convertId();
            hashMap.put(vsFieldMinMaxAggregation.getId(), vsFieldMinMaxAggregation.minus());
        }
        return hashMap;
    }

    @Override
    public Iterator<SensorMaxMinAggregation> cencusSensorElectricUseageByTimeRange(Date startTime, Date endTime) {
        Datastore ds = virtualSensorDataDAO.getDatastore();
        AggregationPipeline aggregationPipeline = ds.createAggregation(VirtualSensorData.class);
        Query<VirtualSensorData> query = virtualSensorDataDAO.createQuery();
        aggregationPipeline
                .match(query.filter("comp_type", "electricmeter_sensor")
                        .field("power").greaterThan(0)
                        .field("time").greaterThan(startTime)
                        .field("time").lessThan(endTime))
                .group(Group.id(Group.grouping("sensor_id"), Group.grouping("comp_id")),
                        Group.grouping("max", Group.max("power")), Group.grouping("min", Group.min("power")));
        return aggregationPipeline.aggregate(SensorMaxMinAggregation.class);
    }

    @Override
    public SensorMaxMinAggregation cencusSensorElectricUseageBySensorAndTimeRange(String sensor_id, Date startTime, Date endTime) {
        Datastore ds = virtualSensorDataDAO.getDatastore();
        AggregationPipeline aggregationPipeline = ds.createAggregation(VirtualSensorData.class);
        Query<VirtualSensorData> query = virtualSensorDataDAO.createQuery();
        aggregationPipeline
                .match(query.filter("comp_type", "electricmeter_sensor")
                        .filter("sensor_id", sensor_id)
                        .field("power").greaterThan(0)
                        .field("time").greaterThan(startTime)
                        .field("time").lessThan(endTime))
                .group(Group.id(Group.grouping("sensor_id"), Group.grouping("comp_id")),
                        Group.grouping("max", Group.max("power")), Group.grouping("min", Group.min("power")));
        Iterator<SensorMaxMinAggregation> aggregation = aggregationPipeline.aggregate(SensorMaxMinAggregation.class);
        return aggregation.hasNext() ? aggregation.next() : null;
    }

    @Override
    public SensorMaxMinAggregation cencusSensorElectricUseageBySensorAndTimeRange(VirtualSensor sensor, Date startTime, Date endTime) {
        if (null == sensor) {
            return null;
        }
        return cencusSensorElectricUseageBySensorAndTimeRange(sensor.getSensor_id(), startTime, endTime);
    }

    @Override
    public Iterator<SensorMaxMinAggregation> cencusSensorWaterHandlerByTimeRange(Date startTime, Date endTime) {
        Datastore ds = virtualSensorDataDAO.getDatastore();
        AggregationPipeline aggregationPipeline = ds.createAggregation(VirtualSensorData.class);
        Query<VirtualSensorData> query = virtualSensorDataDAO.createQuery();
        aggregationPipeline
                .match(query.filter("comp_type", "flowmeter_sensor")
                        .field("positive_total").greaterThan(0)
                        .field("time").greaterThan(startTime)
                        .field("time").lessThan(endTime))
                .group(Group.id(Group.grouping("sensor_id"), Group.grouping("comp_id")), Group.grouping("max", Group.max("positive_total")), Group.grouping("min", Group.min("positive_total")));
        return aggregationPipeline.aggregate(SensorMaxMinAggregation.class);
    }

    @Override
    public SensorMaxMinAggregation cencusSensorWaterHandlerBySensorAndTimeRange(String sensor_id, Date startTime, Date endTime) {
        Datastore ds = virtualSensorDataDAO.getDatastore();
        AggregationPipeline aggregationPipeline = ds.createAggregation(VirtualSensorData.class);
        Query<VirtualSensorData> query = virtualSensorDataDAO.createQuery();
        aggregationPipeline
                .match(query.filter("comp_type", "flowmeter_sensor")
                        .filter("sensor_id", sensor_id)
                        .field("positive_total").greaterThan(0)
                        .field("time").greaterThan(startTime)
                        .field("time").lessThan(endTime))
                .group(Group.id(Group.grouping("sensor_id"), Group.grouping("comp_id")), Group.grouping("max", Group.max("positive_total")), Group.grouping("min", Group.min("positive_total")));
        Iterator<SensorMaxMinAggregation> aggregation = aggregationPipeline.aggregate(SensorMaxMinAggregation.class);
        return aggregation.hasNext() ? aggregation.next() : null;
    }

    @Override
    public SensorMaxMinAggregation cencusSensorWaterHandlerBySensorAndTimeRange(VirtualSensor sensor, Date startTime, Date endTime) {
        if (null == sensor) {
            return null;
        }
        return cencusSensorWaterHandlerBySensorAndTimeRange(sensor.getSensor_id(), startTime, endTime);
    }

    @Override
    public ArrayList<SensorDayHighWaterLevelCountCencus> cencusSensorHighWaterLevelByTimeRange(String sensor_id, Date startTime, Date endTime) {
        VirtualSensor sensor = virtualSensorDAO.findById(sensor_id);
        return cencusSensorHighWaterLevelByTimeRange(sensor, startTime, endTime);
    }

    @Override
    public ArrayList<SensorDayHighWaterLevelCountCencus> cencusSensorHighWaterLevelByTimeRange(VirtualSensor sensor, Date startTime, Date endTime) {
        ArrayList<SensorDayHighWaterLevelCountCencus> cencusList = new ArrayList<SensorDayHighWaterLevelCountCencus>();
        String sensor_id = sensor.getSensor_id();
        for (Component component : sensor.getComponents()) {
            if (component.getInstance_type() != 4105) {
                continue;
            }
            String comp_id = component.getComp_id();

            Iterator<VirtualSensorData> dataIterator = virtualSensorDataDAO.find(
                    virtualSensorDataDAO.createQuery()
                            .filter("sensor_id", sensor_id)
                            .filter("comp_id", comp_id)
                            .field("time").greaterThan(startTime)
                            .field("time").lessThan(endTime)
                            .order("time")
            ).iterator();
            int count = 0;
            boolean isOnoff = false;
            if (dataIterator.hasNext()) {
                isOnoff = dataIterator.next().isOnoff();
            }
            while (dataIterator.hasNext()) {
                VirtualSensorData data = dataIterator.next();
                if (isOnoff == data.isOnoff()) {
                    continue;
                }
                isOnoff = data.isOnoff();
                count++;
            }
            SensorDayHighWaterLevelCountCencus cencus = new SensorDayHighWaterLevelCountCencus();
            cencus.setSensor_id(sensor_id);
            cencus.setComp_id(comp_id);
            cencus.setComp_type(component.getType());
            cencus.setComp_name(component.getName());
            cencus.setInstance_type(component.getInstance_type());
            cencus.setTime(startTime);
            cencus.setValue(count);
            cencusList.add(cencus);
        }
        return cencusList;
    }

    @Override
    public ArrayList<SensorDayMolErrorCountCencus> cencusSensorMainonloadErrorCountByTimeRange(String sensor_id, Date startTime, Date endTime) {
        VirtualSensor sensor = virtualSensorDAO.findById(sensor_id);
        return cencusSensorMainonloadErrorCountByTimeRange(sensor, startTime, endTime);
    }

    @Override
    public ArrayList<SensorDayMolErrorCountCencus> cencusSensorMainonloadErrorCountByTimeRange(VirtualSensor sensor, Date startTime, Date endTime) {
        ArrayList<SensorDayMolErrorCountCencus> cencusList = new ArrayList<SensorDayMolErrorCountCencus>();
        String sensor_id = sensor.getSensor_id();
        for (Component component : sensor.getComponents()) {
            if (component.getInstance_type() != 4104) {
                continue;
            }
            String comp_id = component.getComp_id();

            Iterator<VirtualSensorData> dataIterator = virtualSensorDataDAO.find(
                    virtualSensorDataDAO.createQuery()
                            .filter("sensor_id", sensor_id)
                            .filter("comp_id", comp_id)
                            .field("time").greaterThan(startTime)
                            .field("time").lessThan(endTime)
                            .order("time")
            ).iterator();
            int count = 0;
            boolean isOnoff = false;
            if (dataIterator.hasNext()) {
                isOnoff = dataIterator.next().isOnoff();
            }
            while (dataIterator.hasNext()) {
                VirtualSensorData data = dataIterator.next();
                if (isOnoff == data.isOnoff()) {
                    continue;
                }
                isOnoff = data.isOnoff();
                count++;
            }
            SensorDayMolErrorCountCencus cencus = new SensorDayMolErrorCountCencus();
            cencus.setSensor_id(sensor_id);
            cencus.setComp_id(comp_id);
            cencus.setComp_type(component.getType());
            cencus.setComp_name(component.getName());
            cencus.setInstance_type(component.getInstance_type());
            cencus.setTime(startTime);
            cencus.setValue(count);
            cencusList.add(cencus);
        }
        return cencusList;
    }

    @Override
    public ArrayList<ComponentDayWorkCencus> cencusSensorComponentWorkTimeByTimeRange(String sensor_id, Date startDate, Date endDate) {
        VirtualSensor sensor = virtualSensorDAO.findById(sensor_id);
        return cencusSensorComponentWorkTimeByTimeRange(sensor, startDate, endDate);
    }

    public ArrayList<ComponentDayWorkCencus> cencusSensorComponentWorkTimeByTimeRange(VirtualSensor sensor, Date startDate, Date endDate) {
        ArrayList<ComponentDayWorkCencus> componentDayWorkCencuseList = new ArrayList<ComponentDayWorkCencus>();
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        String sensor_id = sensor.getSensor_id();

        for (Component component : sensor.getComponents()) {
            String comp_type = component.getType();
            if (!StringUtils.equals("onboard_controller", comp_type)) {
                continue;
            }
            String comp_id = component.getComp_id();

            Iterator<VirtualSensorData> dataIterator = virtualSensorDataDAO.find(
                    virtualSensorDataDAO.createQuery()
                            .filter("sensor_id", sensor_id)
                            .filter("comp_id", comp_id)
                            .field("time").greaterThan(startDate)
                            .field("time").lessThan(endDate)
                            .order("time")
            ).iterator();
            Date on = null;
            long distance = 0;
            boolean latestIsOn = false;
            while (dataIterator.hasNext()) {
                VirtualSensorData sensorData = dataIterator.next();
                if (sensorData.isOnoff()) {
                    if (latestIsOn && dataIterator.hasNext()) {
                        continue;
                    }
                    latestIsOn = true;
                    on = sensorData.getTime();
                    if (!dataIterator.hasNext()) {
                        distance += endTime - on.getTime();
                    }
                } else {
                    if (!latestIsOn) {
                        continue;
                    }
                    latestIsOn = false;
                    if (on == null) {
                        distance = sensorData.getTime().getTime() - startTime;
                    } else {
                        distance += sensorData.getTime().getTime() - on.getTime();
                    }
                }
            }

            ComponentDayWorkCencus cencus = new ComponentDayWorkCencus();
            cencus.setSensor_id(sensor_id);
            cencus.setComp_id(comp_id);
            cencus.setComp_name(component.getName());
            cencus.setComp_type(component.getType());
            cencus.setInstance_type(component.getInstance_type());
            cencus.setTime(startDate);
            cencus.setValue(distance);
            componentDayWorkCencuseList.add(cencus);
        }
        return componentDayWorkCencuseList;
    }
}
