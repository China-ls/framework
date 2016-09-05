package com.infinite.water.service.impl;

import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.Department;
import com.infinite.water.service.DepartmentService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service("DepartmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public Department getDepartment() {
        String json = null;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("xdepartment.json");
            is = resource.getInputStream();
            json = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, Department.class);
    }

    @Override
    public Department getDepartmentRoute() {
        String json = null;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("xdepartment_route.json");
            is = resource.getInputStream();
            json = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, Department.class);
    }
}
