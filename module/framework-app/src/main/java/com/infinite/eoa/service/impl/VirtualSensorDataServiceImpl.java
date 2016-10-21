package com.infinite.eoa.service.impl;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.persistent.IMongoDAO;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorDataService")
public class VirtualSensorDataServiceImpl implements VirtualSensorDataService {
    private static Logger LOG = LoggerFactory.getLogger(VirtualSensorDataServiceImpl.class);

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
    @Autowired
    private VirtualSensorDataDAO virtualSensorDataDAO;

    /*public List<VirtualSensorData> save(String data) {
        List<VirtualSensorData> sensorDatas = JsonUtil.fromJson(data, new TypeToken<List<VirtualSensorData>>() {
        }.getType());
        for (VirtualSensorData vsd : sensorDatas) {
            virtualSensorDataDAO.save(vsd);
        }
        virtualSensorService.onSensorDataCome(sensorDatas);
        return sensorDatas;
    }*/

    @Override
    public List<Document> save(String data) {
        Document[] documents = null;
        if (data.startsWith("[")) {
            documents = JsonUtil.fromJson(data, new TypeToken<Document[]>(){}.getType());
        } else {
            documents = new Document[1];
            documents[0] = JsonUtil.fromJson(data, Document.class);
        }

        ArrayList<Document> documentList = new ArrayList<Document>();

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
            documentList.add(document);
        }
        mongoDAO.insertMany(dbname, collectionName, documentList);
        if (LOG.isDebugEnabled()) {
            LOG.debug("save sensor data : {}", documentList);
        }
        virtualSensorService.onSensorDataCome(documentList);
        return documentList;
    }

    @Override
    public ArrayList<VirtualSensorData> findLatestBySensorId(String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        ArrayList<VirtualSensorData> virtualSensorDatas = new ArrayList<VirtualSensorData>();
        VirtualSensor sensor = virtualSensorService.findById(sensorid);
        //TODO 修改为 Aggregates 方式取数据
        if (null != sensor) {
            for (Component component : sensor.getComponents()) {
                VirtualSensorData vsd = virtualSensorDataDAO.find(
                        virtualSensorDataDAO.createQuery()
                                .filter("sensor_id", sensorid)
                                .filter("comp_id", component.getComp_id())
                                .order("-time")
                ).get();
                if (null != vsd) {
                    virtualSensorDatas.add(vsd);
                }
            }
        }
        return virtualSensorDatas;
    }
    /*public List<Document> findLatestBySensorId(String appkey, String sensorid) {
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
                )).sort(Sorts.descending("time")).first();
                if (null != document) {
                    documentList.add(document);
                }
            }
        }
        return documentList;
    }*/

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
    public Document findFieldDegreeByInterval(
            String appkey, String sensorid, String fieldname,
            int type)
            throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();

        if (!StringUtils.isEmpty(fieldname)) {
            MongoCollection<Document> mongoCollection = mongoDAO.getCollection(dbname, collectionName);
            DateTime dateTime = new DateTime();
            switch (type) {
                case 1://天
                    dateTime = dateTime.minusDays(30);
                    for (int i = 0; i < 30; i++) {
                        dateTime = dateTime.plusDays(1);
                        values.add(
                                getDegreeByTime(mongoCollection, sensorid, fieldname,
                                        TimeUtils.getMinMillsOfDay(dateTime), TimeUtils.getMaxMillsOfDay(dateTime)
                                )
                        );
                        keys.add(
                                dateTime.toString("MM-dd", Locale.CHINESE)
                        );
                    }
                    break;
                case 2://年
                    int month = dateTime.getMonthOfYear();
                    //从1月开始
                    dateTime = dateTime.withMonthOfYear(1);
                    for (int i = 0; i < month; i++) {
                        dateTime = dateTime.plusMonths(1);
                        values.add(
                                getDegreeByTime(mongoCollection, sensorid, fieldname,
                                        TimeUtils.getMinMillsOfDay(dateTime.withDayOfMonth(1)),
                                        TimeUtils.getMaxMillsOfDay(dateTime.plusMonths(1).withDayOfMonth(1).minusDays(1))
                                )
                        );
                        keys.add(
                                dateTime.toString("yyyy-MM", Locale.CHINESE)
                        );
                    }
                    break;
                case 0://小时
                default:
                    int hour = dateTime.getHourOfDay() + 1;
                    dateTime = dateTime.withTime(0, 0, 0, 0);
                    long t1 = 0, t2 = 0;
                    for (int i = 0; i < hour; i++) {
                        if (i == 0) {
                            t1 = dateTime.getMillis();
                        } else {
                            t1 = t2;
                        }
                        dateTime = dateTime.plusHours(1);
                        t2 = dateTime.getMillis();
                        values.add(
                                getDegreeByTime(mongoCollection, sensorid, fieldname, t1, t2)
                        );
                        keys.add(
                                dateTime.toString("HH时", Locale.CHINESE)
                        );
                    }
                    break;
            }
        }

        return new Document("keys", keys).append("values", values);
    }

    private double getDegreeByTime(
            MongoCollection collection, String sensorid,
            String fieldname, long timemin, long timemax) {
        FindIterable<Document> iterable = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.exists(fieldname),
                Filters.gte(fieldname, 0),
                Filters.gte("time", new BsonDateTime(timemin)),
                Filters.lt("time", new BsonDateTime(timemax))
        )).projection(Projections.include(fieldname));
        Document dsc = iterable.sort(Sorts.descending("time")).first();
        Document asc = iterable.sort(Sorts.ascending("time")).first();
        return getDoubleMinus(fieldname, dsc, asc);
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

    @Override
    public Document findWaterDataBySensorId(String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        Document document = new Document();
        MongoCollection<Document> collection = mongoDAO.getCollection(dbname, collectionName);

        //最新的一条数据
        Document latestData = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "flowmeter_sensor")
        )).sort(Sorts.descending("time")).first();
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

    @Override
    public Document findElectricDataBySensorId(String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        Document document = new Document();
        MongoCollection<Document> collection = mongoDAO.getCollection(dbname, collectionName);

        //最新的一条数据
        Document latestData = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "electricmeter_sensor")
        )).sort(Sorts.descending("time")).first();
        document.append("latest", latestData);

        DateTime dateTime = new DateTime();
        BsonDateTime todayMaxBDT = new BsonDateTime(TimeUtils.getMaxMillsOfDay(dateTime));
        BsonDateTime todayMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findToday = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "electricmeter_sensor"),
                Filters.gte("time", new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime))),
                Filters.lt("time", todayMaxBDT)
        )).projection(Projections.include("power")).limit(1);
        Document todayMax = findToday.sort(Sorts.descending("time")).first();
        Document todayMin = findToday.sort(Sorts.ascending("time")).first();
        document.append("today", getDoubleMinus("power", todayMax, todayMin));

        Document yestodayMin = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "electricmeter_sensor"),
                Filters.gte("time", new BsonDateTime(TimeUtils.getMaxMillsOfDay(dateTime.minusDays(1)))),
                Filters.lt("time", todayMinBDT)
        )).projection(Projections.include("power")).limit(1)
                .sort(Sorts.ascending("time")).first();
        document.append("yestoday", getDoubleMinus("power", todayMin, yestodayMin));

        dateTime = dateTime.withDayOfMonth(1);
        BsonDateTime monthMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findMonth = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "electricmeter_sensor"),
                Filters.gte("time", monthMinBDT),
                Filters.lt("time", todayMaxBDT)
        )).projection(Projections.include("power")).limit(1);
        Document monthMax = findMonth.sort(Sorts.descending("time")).first();
        Document monthMin = findMonth.sort(Sorts.ascending("time")).first();
        document.append("month", getDoubleMinus("power", monthMax, monthMin));

        dateTime = dateTime.minusMonths(1);
        BsonDateTime lastMonthMinBDT = new BsonDateTime(TimeUtils.getMinMillsOfDay(dateTime));
        FindIterable<Document> findLastMonth = mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.eq("comp_type", "electricmeter_sensor"),
                Filters.gte("time", lastMonthMinBDT),
                Filters.lt("time", monthMinBDT)
        )).projection(Projections.include("power")).limit(1);
        Document lastMonthMax = findLastMonth.sort(Sorts.descending("time")).first();
        Document lastMonthMin = findLastMonth.sort(Sorts.ascending("time")).first();
        document.append("last_month", getDoubleMinus("power", lastMonthMax, lastMonthMin));

        return document;
    }

    @Override
    public Document getImageData(
            String appkey, String sensorid,
            long start, long end) {
        applicationService.applicationExsist(appkey);
        Document document = new Document();
        document.append("device", virtualSensorService.findById(sensorid));
        ArrayList<Document> documents = new ArrayList<Document>();
        MongoCollection<Document> collection = mongoDAO.getCollection(dbname, collectionName);
        mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.exists("image"),
                Filters.gte("time", new BsonDateTime(start)),
                Filters.lt("time", new BsonDateTime(end))
        )).projection(Projections.exclude("image"))
                .sort(Sorts.descending("time")).into(documents);
        document.append("data", documents);
        return document;
    }

    @Override
    public Document getImageDataTop(
            String appkey, String sensorid) {
        applicationService.applicationExsist(appkey);
        Document document = new Document();
        document.append("device", virtualSensorService.findById(sensorid));
        ArrayList<Document> documents = new ArrayList<Document>();
        MongoCollection<Document> collection = mongoDAO.getCollection(dbname, collectionName);
        mongoDAO.find(collection, Filters.and(
                Filters.eq("sensor_id", sensorid),
                Filters.exists("image")
        )).projection(Projections.exclude("image"))
                .sort(Sorts.descending("time")).limit(20).into(documents);
        document.append("data", documents);
        return document;
    }

    private Double getDoubleMinus(String field, Document max, Document min) {
        if (null == max || null == min
                || !max.containsKey(field)
                || !min.containsKey(field)) {
            return 0.0D;
        } else {
            double distance = Math.abs(max.getDouble(field) - min.getDouble(field));
            return NumberUtils.toDouble(decimalFormat.format(distance).replace(",", ""));
        }
    }

    @Override
    public Document cencusTodayAndMonthWorkTimeBySensorId(String appkey, String sensor_id) {
        DateTime dateTime = new DateTime();
        long endTime = TimeUtils.getMinMillsOfDay(dateTime);
        long startTime = TimeUtils.getMinMillsOfDay(dateTime.minusDays(1));

        HashMap<String, Date> latestCencus = new HashMap<String, Date>();
        HashMap<String, Long> dayCencus = new HashMap<String, Long>();
        Iterator<VirtualSensorData> sdIt = virtualSensorDataDAO.find(
                virtualSensorDataDAO.createQuery()
                        .filter("sensor_id", sensor_id)
                        .field("time").greaterThanOrEq(new Date(startTime))
                        .field("time").lessThan(new Date(endTime))
                        .order("comp_type,time")
        ).iterator();
        while (sdIt.hasNext()) {
            VirtualSensorData sensorData = sdIt.next();
            String cid = sensorData.getComp_id();
            Date time = sensorData.getTime();
            long ct = time.getTime();
            Date lateast = latestCencus.get(cid);
            if (sensorData.isOnoff()) {//开启的
                if (null == lateast) {
                    latestCencus.put(cid, time);
                }
            } else if (!sensorData.isOnoff() && null != lateast) {//关闭的
                long distance = ct - lateast.getTime();
                Long def = dayCencus.get(cid);
                def = def == null ? 0 : def;
                dayCencus.put(cid, def + distance);
            }
        }
        return null;
    }
}
