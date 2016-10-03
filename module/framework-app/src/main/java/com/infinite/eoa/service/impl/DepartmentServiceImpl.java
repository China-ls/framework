package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.persistent.DepartmentDAO;
import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.service.DepartmentService;
import com.infinite.eoa.service.exception.InvalidDataException;
import com.mongodb.WriteResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service("DepartmentService")
public class DepartmentServiceImpl extends AbstractPagerService<Department> implements DepartmentService {
    private static Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return departmentDAO;
    }

    @Override
    public int deleteById(String id) {
        WriteResult result = departmentDAO.deleteById(new ObjectId(id));
        return result.getN();
    }

    @Override
    public Document listByType(String type) {
        String json = null;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource(
                    StringUtils.equals(type, "device") ? "xdepartment.json" : "xdepartment_route.json"
            );
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
        return JsonUtil.fromJson(json, Document.class);
    }

    @Override
    public Department save(Department department) {
        if (null == department) {
            throw new InvalidDataException("department is empty");
        }
        Key<Department> key = departmentDAO.save(department);
        department.setId((ObjectId) key.getId());
        return department;
    }

    @Override
    public Pager<Department> listPagerAll(int page, int size, boolean withEntity) {
        return listPagerByDepartmentType(page, size, -1, withEntity);
    }

    @Override
    public Pager<Department> listPagerByDepartmentType(int page, int size, int type, boolean withEntity) {
        Query query = departmentDAO.createQuery();
        if (type != -1) {
            query.filter("type =", type);
        }
//        if (!withEntity) {
//            query.filter("nodeType =", type);
//        }
        query.order("path");

        Pager<Department> pager = listPager(page, size, query);
        pager.setData(sortDepartmentTree(pager.getData()));
        return pager;
    }

    private List<Department> sortDepartmentTree(List<Department> list) {
        if (null != list) {
            int pageSize = list.size();

            String path = null;
            String childpath = null;
            String id = null;
            int index = -1;
            for (int i = 0; i < pageSize; i++) {
                Department item = list.get(i);
                path = item.getPath();
                if (path == null) {
                    continue;
                }
                id = item.getIdHexString();
                childpath = path + id + ",";
                index = i;
                for (int j = index; j < pageSize; j++) {
                    Department child = list.get(j);
                    if (childpath.equals(child.getPath())) {
                        if (j - index > 1) {
                            index++;
                            Department willmove = list.remove(j);
                            list.add(index, willmove);
                        }
                    }
                }
            }
        }
        return list;
    }
}
