package com.infinite.water.entity.net;

import com.infinite.water.entity.EmployeeDepartment;

import java.util.List;

/**
 * @author hx on 16-8-23.
 */
public class ListEmployeeDepartmentRequestResult extends BasicReuqestResult {

    private List<EmployeeDepartment> obj;

    public List<EmployeeDepartment> getObj() {
        return obj;
    }

    public void setObj(List<EmployeeDepartment> obj) {
        this.obj = obj;
    }
}
