package com.infinite.eoa.service.impl;

import com.infinite.eoa.persistent.DepartmentEntityDAO;
import com.infinite.eoa.service.DepartmentEntityService;
import com.infinite.eoa.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DepartmentEntityService")
public class DepartmentEntityServiceImpl implements DepartmentEntityService {
    private static Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentEntityDAO departmentEntityDAO;

}
