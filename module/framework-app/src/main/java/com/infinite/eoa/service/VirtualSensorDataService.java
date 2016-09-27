package com.infinite.eoa.service;

import com.infinite.eoa.service.exception.ApplicationNotExsistException;
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
            String fieldname, String... days)
            throws ApplicationNotExsistException;

    /**
     * @param appkey    APPKEY
     * @param sensorid  设备ID
     * @param fieldname 字段名字
     * @param type      0 小时 1 天 2 月
     * @return
     * @throws ApplicationNotExsistException
     */
    Document findFieldDegreeByInterval(
            String appkey, String sensorid,
            String fieldname, int type)
            throws ApplicationNotExsistException;

    /**
     * @param sensorid    设备ID
     * @param skip        跳过数据量
     * @param limit       返回总数
     * @param exsistArray 存在的字段
     * @param fieldArray  返回的字段
     * @param ascSort     asc排序字段
     * @param dscSort     dsc排序字段
     */
    List<Document> findByFieldExsistAndProjection(
            String appkey, String sensorid, int skip,
            int limit, List<String> exsistArray, List<String> fieldArray,
            List<String> ascSort, List<String> dscSort);

    Document findById(String appkey, String id, List<String> fieldArray);

    Document findWaterDataBySensorId(
            String appkey, String sensorid);

    Document findElectricDataBySensorId(String appkey, String sensorid);

    Document getImageData(String appkey, String sensorid, long start, long end);
}
