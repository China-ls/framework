package com.infinite.framework.service.impl;

import com.infinite.framework.core.persistent.IMongoDAO;
import com.infinite.framework.service.VirtualSensorDataService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorDataService")
public class VirtualSensorDataServiceImpl implements VirtualSensorDataService {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorDataServiceImpl.class);

    @Autowired
    private IMongoDAO mongoDAO;

    @Value("#{dbConfig.sensor_db}")
    private String dbname;
    @Value("#{dbConfig.sensor_data_collection}")
    private String collectionName;

    @Override
    public void save(String data) {
        Document document = Document.parse(data);
        document.append("sensor_id", document.get("uuid"));
        document.remove("uuid");
        mongoDAO.insertOne(dbname, collectionName, document);
    }
}
