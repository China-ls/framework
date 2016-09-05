package com.infinite.water.controller;

import com.infinite.water.core.controller.AbstractController;
import com.infinite.water.core.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/sys")
@Controller("SystemInformationController")
public class SystemInformationController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(SystemInformationController.class);

    @RequestMapping(value = "/info")
    @ResponseBody
    public String getSystemInformation() {
        String information = null;
        try {
            information = HttpUtils.get("http://127.0.0.1:61680/broker.json",
                    Arrays.asList(new HttpUtils.Pair("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
            );
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("error get system information", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("info : {}", information);
        }
        return information;
    }


}
