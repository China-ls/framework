package com.infinite.eoa.service;

import com.infinite.eoa.entity.aggregation.VsDeviceStatusAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceTypeAggregation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface CencusService {
    public ArrayList<VsDeviceTypeAggregation> deviceTypeDistribution();

    public ArrayList<VsDeviceStatusAggregation> deviceOnlineDistribution();

    HashMap<String, Double> cencusPositiveTotalByStation_type(String station_type, long start, long end);

    HashMap<String, Double> cencusPowerByStation_type(String station_type, long start, long end);
}
