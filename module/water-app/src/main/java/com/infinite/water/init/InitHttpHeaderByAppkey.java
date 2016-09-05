package com.infinite.water.init;

import com.infinite.water.core.initializing.Initializing;
import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.entity.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InitHttpHeaderByAppkey implements Initializing {
    private static Logger log = LoggerFactory.getLogger(InitHttpHeaderByAppkey.class);

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("init http HEADER appkey");
        }
        HttpUtils.addDefaultHeader("APPKEY", serverConfig.getAppkey());
        if (log.isDebugEnabled()) {
            log.debug("finish init http HEADER appkey");
        }
    }
}
