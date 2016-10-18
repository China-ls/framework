package com.infinite.eoa.init;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.initializing.Initializing;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.entity.Permission;
import com.infinite.eoa.entity.Role;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.PermissionDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.EmployeeService;
import com.infinite.eoa.service.RoleService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InitDatabase implements Initializing {
    private static Logger log = LoggerFactory.getLogger(InitDatabase.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermissionDAO permissionDAO;
    @Autowired
    private RoleService roleService;

    private String roleAdminName = "超级管理员";
    @Value("#{dbConfig.releace_db_sensor}")
    private boolean releace_db_sensor;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init database");
        }
        long start = System.currentTimeMillis();

        initAdminAccount();

        initSensors();

        ArrayList<Permission> permissions = initPermission();

        initRole(permissions);

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (log.isDebugEnabled()) {
            log.debug("finish init database, cose : {} millseconds", cost);
        }
    }

    private void initAdminAccount() {
        Employee employee = employeeService.getByUsername("admin");
        if (null == employee) {
            employee = new Employee();
            employee.setUsername("admin");
            employee.setPassword("admin");
            employee.setSex(1);
            employee.setEmail("414057419@qq.com");
            employeeService.insert(employee);
        }
    }

    private void initSensors() {
        InputStream vsIs = getClass().getClassLoader().getResourceAsStream("xsensor.json");
        try {
            String vsString = IOUtils.toString(vsIs);
            VirtualSensor[] sensors = JsonUtil.fromJson(vsString, new TypeToken<VirtualSensor[]>() {
            }.getType());
            for (VirtualSensor sensor : sensors) {
                sensor.setOnline(EntityConst.SensorOnline.NO);

                VirtualSensor storedSensor = virtualSensorDAO.findById(sensor.getSensor_id());
                if (releace_db_sensor) {
                    virtualSensorDAO.delete(storedSensor);
                    virtualSensorDAO.save(sensor);
                } else if (null == storedSensor) {
                    virtualSensorDAO.save(sensor);
                }
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("error get xsensor.json data", e);
            }
        } finally {
            IOUtils.closeQuietly(vsIs);
        }
    }

    private ArrayList<Permission> initPermission() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("permission.txt");
        ArrayList<Permission> permissions = new ArrayList<Permission>();
        try {
            Scanner scanner = new Scanner(is);
            HashMap<String, String> idLineMap = new HashMap<String, String>();
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (null == line || line.trim().length() <= 0) {
                    continue;
                }
                Permission permission = new Permission();
                String[] array = line.split(",");

                permission.setName(array[1]);
                permission.setUrl(array[2]);
                permission = mergePermission(permission, array[3], sb, idLineMap);

                idLineMap.put("l" + array[0], permission.getId().toHexString());

                permissions.add(permission);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("error init permission.", e);
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
        return permissions;
    }

    private Permission mergePermission(Permission permission, String linepath, StringBuilder stringBuilder, HashMap<String, String> idLineMap) {
        permission.setId(ObjectId.get());

        Permission storedPermission = permissionDAO.findByname(permission.getName());
        if (null != storedPermission) {
            permission.setId(storedPermission.getId());
        }

        if (null != linepath && linepath.trim().length() > 0 && !linepath.equals(";")) {
            String[] lineArray = linepath.split(";");
            stringBuilder.setLength(0);
            for (String line : lineArray) {
                if (null != line && line.trim().length() > 0) {
                    stringBuilder.append(idLineMap.get("l" + line));
                }
                stringBuilder.append(";");
                permission.setPath(stringBuilder.toString());
            }
        } else {
            permission.setPath(linepath);
        }
        Key<Permission> key = permissionDAO.save(permission);
        permission.setId((ObjectId) key.getId());
        return permission;
    }

    private void initRole(ArrayList<Permission> permissions) {
        Role role = roleService.findByName(roleAdminName);
        if (null == role) {
            role = new Role();
        }
        role.setName(roleAdminName);
        role.setPath(";");
        role.setDescription(roleAdminName);
        role.setStatus(EntityConst.EntityStatus.NORMAL.val);

        role.setPermissions(permissions);
        role = roleService.save(role);
        if (log.isDebugEnabled()) {
            log.debug("System inited role : {}", role);
        }
    }

}
