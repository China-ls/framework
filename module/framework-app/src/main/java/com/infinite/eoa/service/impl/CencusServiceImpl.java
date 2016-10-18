package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.aggregation.VsDeviceStatusAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceTypeAggregation;
import com.infinite.eoa.entity.aggregation.VsFieldMinMaxAggregation;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.CencusService;
import com.infinite.eoa.service.VirtualSensorDataService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
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
    private VirtualSensorDataService virtualSensorDataService;

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
}
