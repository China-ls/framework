package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/temp")
@RestController("TempController")
public class TempController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(TempController.class);

    @RequestMapping("/department")
    @ResponseBody
    public String roles() {
        String department = "";
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("xdepartment.json");
            is = resource.getInputStream();
            department = IOUtils.toString(is);
        } catch (IOException e) {
            log.error("get department.json error.", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return department;
    }

}
