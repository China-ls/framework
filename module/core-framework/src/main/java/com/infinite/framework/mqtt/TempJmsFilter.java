package com.infinite.framework.mqtt;

import com.infinite.framework.core.util.HttpUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TempJmsFilter implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TempJmsFilter.class);

    private String message;
    private String url = "http://127.0.0.1:9998/app/callback/jms";

    public TempJmsFilter(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        log.debug("will call back url : {}, message : {}", url, message);
        try {

            HttpUtils.post(url, new BasicNameValuePair("message", message));
            log.debug("call back url : {} success", url);
        } catch (IOException e) {
            log.error("callback error.", e);
        }
    }

}
