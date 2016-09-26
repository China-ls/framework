package com.infinite.eoa.service.impl;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.persistent.IMongoDAO;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.service.ApplicationService;
import com.infinite.eoa.service.VirtualSensorDataService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorDataService")
public class VirtualSensorDataServiceImpl implements VirtualSensorDataService {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorDataServiceImpl.class);

    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    @Autowired
    private IMongoDAO mongoDAO;

    @Value("#{dbConfig.sensor_db}")
    private String dbname;
    @Value("#{dbConfig.sensor_data_collection}")
    private String collectionName;

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private VirtualSensorService virtualSensorService;

    @Override
    public List<Document> save(String data) {
        Document[] documents = null;
        if (data.startsWith("[")) {
            documents = JsonUtil.fromJson(data, new TypeToken<Document[]>() {
            }.getType());
        } else {
            documents = new Document[1];
            documents[0] = JsonUtil.fromJson(data, Document.class);
        }

        ArrayList<Document> documentList = new ArrayList<Document>();

//        VirtualSensor sensor = virtualSensorService.findById(documents[0].getString("sensor_id"));
//        HashMap<String, String> typeMap = new HashMap<String, String>();
//        for (Component component : sensor.getComponents()) {
//            typeMap.put(component.getComp_id(), component.getType());
//        }

        for (Document document : documents) {
            Object timeObject = document.remove("time");
            long time = 0;
            if (null != timeObject) {
                time = NumberUtils.toLong(timeObject.toString());
            }
            if (time <= 0) {
                time = System.currentTimeMillis();
            }
            document.append("time", new BsonDateTime(time));
//            String compid = document.getString("comp_id");
//            document.append("_id",
//                    DecriptUtil.SHA1(document.getString("sensor_id") + compid + time)
//            );
//            document.append("comp_type", typeMap.get(compid));
            documentList.add(document);
        }
        mongoDAO.insertMany(dbname, collectionName, documentList);
        return documentList;
    }

    @Override
    public List<Document> findLatestBySensorId(String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        ArrayList<Document> documentList = new ArrayList<Document>();
        VirtualSensor sensor = virtualSensorService.findById(sensorid);
        //TODO 修改为 Aggregates 方式取数据
        if (null != sensor) {
            MongoCollection<Document> mongoCollection = mongoDAO.getCollection(dbname, collectionName);
            for (Component component : sensor.getComponents()) {
                Document document = mongoDAO.find(mongoCollection, Filters.and(
                        Filters.eq("sensor_id", sensorid),
                        Filters.eq("comp_id", component.getComp_id())
                )).sort(Sorts.ascending("time")).first();
                if (null != document) {
                    documentList.add(document);
                }
            }
        }
        return documentList;
    }

    @Override
    public ArrayList<Document> findBySensorIdAndTimeDistance(String appkey, String sensorid, String comp_type, long start, long end) {
        applicationService.applicationExsist(appkey);
//        VirtualSensor sensor = virtualSensorService.findById(sensorid);
        ArrayList<Document> documents = new ArrayList<Document>();
//        if (null != sensor) {
        long safeEnd = end <= 0 ? System.currentTimeMillis() : end;
//            HashMap<String, String> componentTypes = new HashMap<String, String>();
//            for (Component component : sensor.getComponents()) {
//                componentTypes.put(component.getComp_id(), component.getType());
//            }
        mongoDAO.find(dbname, collectionName, StringUtils.isEmpty(comp_type) ?
                Filters.and(Filters.eq("sensor_id", sensorid),
                        Filters.gte("time", new BsonDateTime(start)),
                        Filters.lt("time", new BsonDateTime(safeEnd)))
                : Filters.and(Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", comp_type),
                Filters.gte("time", new BsonDateTime(start)),
                Filters.lt("time", new BsonDateTime(safeEnd)))
        ).into(documents);

//            for (Document document : documents) {
//                document.append("", "");
//            }
//        }
        return documents;
    }

    @Override
    public List<Document> findBySensorIdAndFilter(String id, String filter) {
        return null;
    }

    public Document findFieldChangeByDays(
            String appkey, String sensorid,
            String fieldname, String... days)
            throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<Float> values = new ArrayList<Float>();

        if (!ArrayUtils.isEmpty(days)) {
            MongoCollection<Document> mongoCollection = mongoDAO.getCollection(dbname, collectionName);

            for (String day : days) {
                if (StringUtils.isEmpty(day)) {
                    continue;
                }
                keys.add(day);
                long startTime = TimeUtils.getMinMillsOfDay(day);
                long endTime = TimeUtils.getMaxMillsOfDay(day);

                FindIterable<Document> iterable = mongoDAO.find(mongoCollection, Filters.and(
                        Filters.eq("sensor_id", sensorid),
                        Filters.exists(fieldname),
                        Filters.gt("time", new BsonDateTime(startTime)),
                        Filters.lt("time", new BsonDateTime(endTime))
                )).projection(Projections.include(fieldname)).limit(1);

                Document minDocument = iterable.sort(Sorts.ascending("time")).first();
                Document maxDocument = iterable.sort(Sorts.descending("time")).first();
                if (null != maxDocument) {
                    Object minObject = null == minDocument ? null : minDocument.get(fieldname);
                    Object maxObject = maxDocument.get(fieldname);
                    double min = null == minObject ? 0 : NumberUtils.toDouble(minObject.toString());
                    double max = NumberUtils.toDouble(maxObject.toString());
                    values.add(NumberUtils.toFloat(decimalFormat.format(Math.abs(max - min)).replace(",", "")));
                } else {
                    values.add(0F);
                }
            }
        }

        return new Document("keys", keys).append("values", values);
    }

    @Override
    public Document findFieldDegreeByDayInterval(
            String appkey, String sensorid, String fieldname,
            int interval, int count)
            throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<Float> values = new ArrayList<Float>();

        if (count > 0) {
            MongoCollection<Document> mongoCollection = mongoDAO.getCollection(dbname, collectionName);
            DateTime dateTime = new DateTime();
            String latestDay = null, day = null;
            long latestTimeMax = 0, latestTimeMin = 0, timeMax = 0, timeMin = 0;
            for (int i = 0; i <= count; i++) {
                dateTime = dateTime.minusDays(i * interval);
                timeMax = TimeUtils.getMaxMillsOfDay(dateTime);
                timeMin = TimeUtils.getMinMillsOfDay(dateTime);
                day = dateTime.toString("MM-dd", Locale.CHINESE);
                if (i == 0) {
                    latestDay = day;
                    latestTimeMax = timeMax;
                    latestTimeMin = timeMin;
                    continue;
                }
                String key = day + "/" + latestDay;

                keys.add(key);

                FindIterable<Document> iterable = mongoDAO.find(mongoCollection, Filters.and(
                        Filters.eq("sensor_id", sensorid),
                        Filters.exists(fieldname),
                        Filters.gt("time", new BsonDateTime(latestTimeMin)),
                        Filters.lt("time", new BsonDateTime(timeMax))
                )).projection(Projections.include(fieldname)).limit(1);

                Document minDocument = iterable.sort(Sorts.ascending("time")).first();
                Document maxDocument = iterable.sort(Sorts.descending("time")).first();
                if (null != maxDocument) {
                    Object minObject = null == minDocument ? null : minDocument.get(fieldname);
                    Object maxObject = maxDocument.get(fieldname);
                    double min = null == minObject ? 0 : NumberUtils.toDouble(minObject.toString());
                    double max = NumberUtils.toDouble(maxObject.toString());
                    values.add(NumberUtils.toFloat(decimalFormat.format(Math.abs(max - min)).replace(",", "")));
                } else {
                    values.add(0F);
                }
                latestDay = day;
                latestTimeMax = timeMax;
                latestTimeMin = timeMin;
            }
        }

        return new Document("keys", keys).append("values", values);
    }

    @Override
    public List<Document> findByFieldExsistAndProjection(
            String appkey, String sensorid, int skip, int limit,
            List<String> exsistArray, List<String> fieldArray,
            List<String> ascSort, List<String> dscSort) {
        applicationService.applicationExsist(appkey);
        ArrayList<Bson> queryFilters = new ArrayList<Bson>();
        ArrayList<Document> documents = new ArrayList<Document>();
        queryFilters.add(Filters.eq("sensor_id", sensorid));
        if (exsistArray != null && exsistArray.size() > 0) {
            for (String exist : exsistArray) {
                queryFilters.add(Filters.exists(exist));
            }
        }
        FindIterable<Document> findIterable = mongoDAO.find(
                dbname, collectionName, Filters.and(queryFilters));
        if (null != fieldArray && fieldArray.size() > 0) {
            findIterable.projection(Projections.include(fieldArray));
        }
        findIterable.skip(skip);
        if (limit > 0) {
            findIterable.limit(limit);
        }
        if (null != ascSort && ascSort.size() > 0) {
            findIterable.sort(Sorts.ascending(ascSort));
        }
        if (null != dscSort && dscSort.size() > 0) {
            findIterable.sort(Sorts.descending(dscSort));
        }

        return findIterable.into(documents);
    }

    @Override
    public Document findById(String appkey, String id, List<String> fieldArray) {
        applicationService.applicationExsist(appkey);
        FindIterable<Document> findIterable = mongoDAO.find(
                dbname, collectionName, Filters.eq("_id", new ObjectId(id)));
        if (null != fieldArray && fieldArray.size() > 0) {
            findIterable.projection(Projections.include(fieldArray));
        }
        return findIterable.limit(1).first();
    }

    @Override//electricmeter_sensor
    public Document findWaterDataBySensorId(String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        Document document = new Document();
        MongoCollection<Document> collection = mongoDAO.getCollection(dbname, collectionName);

        //最新的一条数据
        Document latestData = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor")
        )).sort(Sorts.ascending("time")).first();
        document.append("latest", latestData);

        DateTime dateTime = new DateTime();

        BsonDateTime todayMaxBDT = new BsonDateTime(TimeUtils.getMaxMillsOfDay(dateTime));
        BsonDateTime todayMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findToday = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor"),
                Filters.gte("time", new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime))),
                Filters.lt("time", todayMaxBDT)
        )).projection(Projections.include("positive_total")).limit(1);
        Document todayMax = findToday.sort(Sorts.descending("time")).first();
        Document todayMin = findToday.sort(Sorts.ascending("time")).first();
        document.append("today", getDoubleMinus("positive_total", todayMax, todayMin));

        Document yestodayMin = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor"),
                Filters.gte("time", new BsonDateTime(TimeUtils.getMaxMillsOfDay(dateTime.minusDays(1)))),
                Filters.lt("time", todayMinBDT)
        )).projection(Projections.include("positive_total")).limit(1)
                .sort(Sorts.ascending("time")).first();
        document.append("yestoday", getDoubleMinus("positive_total", todayMin, yestodayMin));

        dateTime = dateTime.withDayOfMonth(1);
        BsonDateTime monthMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findMonth = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor"),
                Filters.gte("time", monthMinBDT),
                Filters.lt("time", todayMaxBDT)
        )).projection(Projections.include("positive_total")).limit(1);
        Document monthMax = findMonth.sort(Sorts.descending("time")).first();
        Document monthMin = findMonth.sort(Sorts.ascending("time")).first();
        document.append("month", getDoubleMinus("positive_total", monthMax, monthMin));

        dateTime = dateTime.minusMonths(1);
        BsonDateTime lastMonthMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findLastMonth = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor"),
                Filters.gte("time", lastMonthMinBDT),
                Filters.lt("time", monthMinBDT)
        )).projection(Projections.include("positive_total")).limit(1);
        Document lastMonthMax = findLastMonth.sort(Sorts.descending("time")).first();
        Document lastMonthMin = findLastMonth.sort(Sorts.ascending("time")).first();
        document.append("last_month", getDoubleMinus("positive_total", lastMonthMax, lastMonthMin));

        return document;
    }

    private Double getDoubleMinus(String field, Document max, Document min) {
        if (null == max || null == min
                || !max.containsKey(field)
                || !min.containsKey(field)) {
            return 0.0D;
        } else {
            double distance = max.getDouble(field) - min.getDouble(field);
            if (distance > 0) {
                distance = NumberUtils.toDouble(decimalFormat.format(distance).replace(",", ""));
            }
            return distance;
        }
    }
}
