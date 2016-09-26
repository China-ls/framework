package com.infinite.water.service.impl;

import com.google.gson.JsonArray;
import com.infinite.eoa.bson.filter.PersistentObjectFilters;
import com.infinite.eoa.bson.filter.PersistentObjectUpdates;
import com.infinite.eoa.bson.filter.bulk.BulkWrite;
import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.Employee;
import com.infinite.water.entity.EmployeeDepartment;
import com.infinite.water.entity.ServerConfig;
import com.infinite.water.entity.net.ListEmployeeDepartmentRequestResult;
import com.infinite.water.entity.net.ListRequestResult;
import com.infinite.water.entity.net.NotSureRequestResult;
import com.infinite.water.entity.net.ObjectRequestResult;
import com.infinite.water.service.EmployeeDepartmentService;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("EmployeeDepartmentService")
public class EmployeeDepartmentServiceImpl extends AbstractService implements EmployeeDepartmentService {
    @Autowired
    private ServerConfig serverConfig;

    @Override
    public List<Document> getAllDepartment() throws IOException {
        ListRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/obj",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment()),
//                        new HttpUtils.Pair("asc", "level,sort"),
                        new HttpUtils.Pair("asc", "sort,level,path")
                ),
                ListRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public List<EmployeeDepartment> getDepartmentTreeRoot() throws IOException {
        ListEmployeeDepartmentRequestResult requestResult = toJsonObject(
                HttpUtils.get(serverConfig.getServerUrl() + "/obj",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment())
                        , new HttpUtils.Pair("asc", "path,sort,level")
                ),
                ListEmployeeDepartmentRequestResult.class
        );
        if (null != requestResult && StringUtils.equals("0", requestResult.getCode())) {
            List<EmployeeDepartment> employeeDepartmentList = requestResult.getObj();
            ArrayList<EmployeeDepartment> sorted = new ArrayList<EmployeeDepartment>();
            int size = employeeDepartmentList.size();

            String path = null;
            String childpath = null;
            String id = null;
            int index = -1;
            for (int i = 0; i < size; i++) {
                EmployeeDepartment item = employeeDepartmentList.get(i);
                path = item.getPath();
                if (path == null) {
                    continue;
                }
                id = item.getIdHexString();
                childpath = path + id + ",";
                index = i;
                for (int j = index; j < size; j++) {
                    EmployeeDepartment child = employeeDepartmentList.get(j);
                    if (childpath.equals(child.getPath())) {
                        if (j - index > 1) {
                            index++;
                            EmployeeDepartment willmove = employeeDepartmentList.remove(j);
                            employeeDepartmentList.add(index, willmove);
                        }
                    }
                }
            }
            return employeeDepartmentList;
        }
        return null;
    }

    @Override
    public Object addDepartment(EmployeeDepartment department) throws IOException {
        department.setType(EmployeeDepartment.TYPE_DEPARTMENT);
        NotSureRequestResult requestResult = toJsonObject(
                HttpUtils.put(serverConfig.getServerUrl() + "/obj",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment()),
                        new HttpUtils.Pair("data", JsonUtil.toJson(department))),
                NotSureRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Object updateDepartment(EmployeeDepartment department) throws IOException {
        if (null == department) {
            return null;
        }
        NotSureRequestResult requestResult = toJsonObject(
                HttpUtils.put(serverConfig.getServerUrl() + "/obj/{id}/updateDepartment".replace("{id}", department.getIdHexString()),
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment()),
                        new HttpUtils.Pair("updates", PersistentObjectUpdates.combineToJson(Arrays.asList(
                                PersistentObjectUpdates.set("address", department.getAddress()),
                                PersistentObjectUpdates.set("name", department.getName()),
                                PersistentObjectUpdates.set("contact", department.getContact()),
                                PersistentObjectUpdates.set("parentId", department.getParent_id()),
                                PersistentObjectUpdates.set("sort", department.getSort())
                        )))
                ),
                NotSureRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Object deleteDepartmentById(String id) throws IOException {
        NotSureRequestResult requestResult = toJsonObject(
                HttpUtils.delete(serverConfig.getServerUrl() + "/obj/delete",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment()),
                        new HttpUtils.Pair("filter",
                                PersistentObjectFilters.eq("_id", id).or(
                                        PersistentObjectFilters.regex("path", ".*," + id + ",.*")
                                ).toJson()
                        )
                ),
                NotSureRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Document addEmployee(EmployeeDepartment department, Employee employee) throws IOException {
        ObjectId id = ObjectId.get();
        employee.setId(id);
        department.setId(null);
        department.setEmployee_id(id.toHexString());
        department.setName(employee.getName());
        department.setType(EmployeeDepartment.TYPE_EMPLOYEE);
        ObjectRequestResult requestResult = toJsonObject(
                HttpUtils.post(serverConfig.getServerUrl() + "/obj/bulk",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment() + "," + serverConfig.getNamespaceEmployee()),
                        new HttpUtils.Pair("bulk",
                                new BulkWrite()
                                        .andInsertOne(Document.parse(JsonUtil.toJson(department)))
                                        .andInsertOne(Document.parse(JsonUtil.toJson(employee)))
                                        .toJson()
                        )
                ),
                ObjectRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    @Override
    public Object deleteEmployeeById(String id) throws IOException {
        String jsonFilter = JsonUtil.toJsonObjectArrayString(
                PersistentObjectFilters.eq("employee_id", id).getBson(),
                PersistentObjectFilters.eq("_id", id).getBson()
        );
        NotSureRequestResult requestResult = toJsonObject(
                HttpUtils.post(serverConfig.getServerUrl() + "/obj/delete/bulk/many",
                        new HttpUtils.Pair("namespace", serverConfig.getNamespaceEmployeeDepartment() + "," + serverConfig.getNamespaceEmployee()),
                        new HttpUtils.Pair("filter", jsonFilter)
                ),
                NotSureRequestResult.class
        );
        return getDocumentFromResponse(requestResult);
    }

    public static void main(String[] args) {
        String json = JsonUtil.toJsonObjectArrayString(
                PersistentObjectFilters.eq("_id", "57ce5cda2dbcae624910700d").getBson()
        );
        System.out.println(json);

        JsonArray jsonArray = JsonUtil.fromJson(json, JsonArray.class);

        System.out.println(jsonArray.toString());

        System.out.println(PersistentObjectFilters.eq("employee_id", "57ce5cda2dbcae624910700d").toJson());
    }
}
