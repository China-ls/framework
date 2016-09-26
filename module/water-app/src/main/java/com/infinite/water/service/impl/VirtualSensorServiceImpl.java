package com.infinite.water.service.impl;

import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.core.util.TimeUtils;
import com.infinite.water.entity.net.ListRequestResult;
import com.infinite.water.entity.net.ObjectRequestResult;
import com.infinite.water.entity.ServerConfig;
import com.infinite.water.service.VirtualSensorService;
import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("VirtualSensorService")
public class VirtualSensorServiceImpl extends AbstractService implements VirtualSensorService {

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public List<Document> getAllVirtualSensor() throws IOException {
        ListRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/all"),
                ListRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Document getVirtualSensor(String id) throws IOException {
        ObjectRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id),
                ObjectRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public List<Document> getVirtualSensorDataBetween(
            String id, String comp_type, long start, long end) throws IOException {
        ListRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id + "/data/between"
                        , new HttpUtils.Pair("comp_type", comp_type)
                        , new HttpUtils.Pair("start", start + "")
                        , new HttpUtils.Pair("end", end + "")),
                ListRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Document getVirtualSensorMonthTotalData(String id) throws IOException {
        String days = TimeUtils.getMonthDaysText();
        ObjectRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id + "/data/fieldchange"
                        , new HttpUtils.Pair("field", "positive_total")
                        , new HttpUtils.Pair("days", days)
                ),
                ObjectRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public List<Document> getTop20Image(String id) throws IOException {
        ListRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id + "/data/projection"
                        , new HttpUtils.Pair("exist", "image")
                        , new HttpUtils.Pair("field", "time,comp_type,sensor_id,comp_id,app_id,image_id")
                        , new HttpUtils.Pair("dscsort", "time")
                        , new HttpUtils.Pair("offset", "0")
                        , new HttpUtils.Pair("limit", "20")
                ),
                ListRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public byte[] getSensorImage(String imageid) throws IOException {
        ObjectRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/data/",
                        new HttpUtils.Pair("id", imageid),
                        new HttpUtils.Pair("field", "image"))
                ,
                ObjectRequestResult.class
        );
        Document document = getDocumentFromResponse(requestResult);
        String jpegBase64 = null == document ? null : document.getString("image");
        if (null == jpegBase64) {
            return null;
        } else {
            return Base64.decodeBase64(jpegBase64.replace("data:image/jpeg;base64,",""));
        }
    }

    @Override
    public List<Document> getVirtualSensorLatestData(String id) throws IOException {
        ListRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id + "/data/latest"),
                ListRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }
}
