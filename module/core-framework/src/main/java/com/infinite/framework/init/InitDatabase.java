package com.infinite.framework.init;

import com.google.gson.reflect.TypeToken;
import com.infinite.framework.core.initializing.Initializing;
import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.persistent.VirtualSensorDAO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public class InitDatabase implements Initializing {
    private static Logger log = LoggerFactory.getLogger(InitDatabase.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init database");
        }
        long start = System.currentTimeMillis();

        InputStream vsIs = getClass().getClassLoader().getResourceAsStream("xsensor.json");
        try {
            String vsString = IOUtils.toString(vsIs);
            VirtualSensor[] sensors = JsonUtil.fromJson(vsString, new TypeToken<VirtualSensor[]>(){}.getType());
            for (VirtualSensor sensor : sensors) {
                virtualSensorDAO.save(sensor);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("error get xsensor.json data", e);
            }
        } finally {
            IOUtils.closeQuietly(vsIs);
        }

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (log.isDebugEnabled()) {
            log.debug("finish init database, cose : {} millseconds", cost);
        }
    }
}
