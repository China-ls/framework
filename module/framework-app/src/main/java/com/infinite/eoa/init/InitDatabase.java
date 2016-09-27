package com.infinite.eoa.init;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.initializing.Initializing;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.EmployeeService;
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

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init database");
        }
        long start = System.currentTimeMillis();

        Employee employee = employeeService.getByUsername("admin");
        if (null == employee) {
            employee = new Employee();
            employee.setUsername("admin");
            employee.setPassword("admin");
            employee.setSex(1);
            employee.setEmail("414057419@qq.com");
            employeeService.insert(employee);
        }

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
