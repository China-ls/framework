package com.infinite.water.init;

import com.infinite.water.core.initializing.Initializing;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class InitOrgnization implements Initializing {
    private static Logger log = LoggerFactory.getLogger(InitOrgnization.class);

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("begain");
        }
        long start = System.currentTimeMillis();

        InputStream vsIs = getClass().getClassLoader().getResourceAsStream("xsensor.json");
        try {
            String vsString = IOUtils.toString(vsIs);
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
            log.debug("finish! cose : {} millseconds", cost);
        }
    }
}
