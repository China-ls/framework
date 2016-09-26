package com.infinite.water.service;

import org.bson.Document;

import java.io.IOException;
import java.util.List;

public interface VirtualSensorService {
    public List<Document> getAllVirtualSensor() throws IOException;

    public Document getVirtualSensor(String id) throws IOException;

    public List<Document> getVirtualSensorDataBetween(String id, String comp_type, long start, long end) throws IOException;

    Document getVirtualSensorMonthTotalData(String id) throws IOException;

    List<Document> getTop20Image(String id) throws IOException;

    byte[] getSensorImage(String imageid) throws IOException;

    List<Document> getVirtualSensorLatestData(String id) throws IOException;
}
