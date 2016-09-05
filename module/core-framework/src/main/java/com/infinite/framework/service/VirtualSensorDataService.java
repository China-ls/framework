package com.infinite.framework.service;

import com.infinite.framework.service.exception.ApplicationNotExsistException;
import org.bson.Document;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorDataService {

    public List<Document> save(String data);

    List<Document> findLatestBySensorId(String appkey, String sensorid);

    ArrayList<Document> findBySensorIdAndTimeDistance(
            String appkey, String sensorid, String comp_type, long start, long end);

    List<Document> findBySensorIdAndFilter(String id, String filter)
            throws ApplicationNotExsistException, SQLException;

    Document findFieldChangeByDays(
            String appkey, String sensorid,
            String fieldname, String ... days)
            throws ApplicationNotExsistException;

    List<Document> findByFieldExsistAndProjection(
            String appkey, String sensorid, int skip,
            int limit, List<String> exsistArray, List<String> fieldArray,
            List<String> ascSort, List<String> dscSort);

    Document findById(String appkey, String id, List<String> fieldArray);
}
