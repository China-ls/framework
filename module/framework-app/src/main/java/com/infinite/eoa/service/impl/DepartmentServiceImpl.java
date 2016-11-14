package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeResourcesLevel;
import com.infinite.eoa.persistent.DepartmentDAO;
import com.infinite.eoa.service.DepartmentService;
import com.infinite.eoa.service.exception.InvalidDataException;
import com.mongodb.WriteResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service("DepartmentService")
public class DepartmentServiceImpl extends AbstractPagerService<Department> implements DepartmentService {
    private static final boolean ISDEBUG = false;
    private static Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return departmentDAO;
    }

    @Override
    public Department findById(String departmentId) {
        return departmentDAO.findById(departmentId);
    }

    @Override
    public int deleteByEntityId(String sensorId) {
        return departmentDAO.deleteByEntityId(sensorId);
    }

    @Override
    public Department findByEntity_id(String entity_id) {
        return departmentDAO.findByEntity_id(entity_id);
    }

    @Override
    public int delete(Department dptEntity) {
        WriteResult result = departmentDAO.delete(dptEntity);
        return result.getN();
    }

    @Override
    public List<Department> findEntityIdsByTypeAndDepartmentId(List<Integer> type, String departmentId) {
        Query<Department> query = departmentDAO.createQuery();
        query.filter("nodeType", Department.NODE_TYPE_ENTITY);
        if (null != type && type.size() > 0) {
            if (type.size() == 1) {
                query.filter("type", type.get(0));
            } else {
                query.field("type").in(type);
            }
        }
        query.field("path").contains(departmentId);
        return departmentDAO.find(query).asList();
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
    public Department listByDepartentType(int type) {
        return null;
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
    public Pager<Department> listPagerAll(int page, int size, int withEntity) {
        return listPagerByDepartmentType(null, page, size, null, withEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pager<Department> listPagerByDepartmentType(Employee employee, int page, int size, Iterable<Integer> type, int withEntity) {
        if (!ISDEBUG && null == employee) {
            return null;
        }
        Query query = departmentDAO.createQuery();
        if (!ISDEBUG) {
            EmployeeResourcesLevel resourceLeve = employee.getResourcesLevel();
            if (null == resourceLeve) {
                return null;
            }
            if (!resourceLeve.isAdmin()) {
                ArrayList<String> resDepartments = resourceLeve.getDepartments();
                ArrayList<Criteria> criteriaList = new ArrayList<Criteria>();
                for (String resDptId : resDepartments) {
                    criteriaList.add((CriteriaContainer) query.criteria("id").equal(new ObjectId(resDptId)));
                    criteriaList.add((CriteriaContainer) query.criteria("path").contains(resDptId));
                }
                Criteria[] criteriaArray = new Criteria[criteriaList.size()];
                criteriaList.toArray(criteriaArray);
                query.or(criteriaArray);
            }
        }
        if (type != null) {
            query.field("type").in(type);
        }
        if (withEntity != 1) {
            query.filter("nodeType", Department.NODE_TYPE_DEFAULT);
        }
        query.order("path,nodeType");

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
