package com.infinite.water.controller;

import com.infinite.water.controller.req.EmployeeDepartmentModel;
import com.infinite.water.controller.req.EmployeeModel;
import com.infinite.water.controller.resp.ResponseCode;
import com.infinite.water.core.controller.response.RestResponse;
import com.infinite.water.entity.EmployeeDepartment;
import com.infinite.water.service.EmployeeDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/dpt/emp")
@Controller("EmployeeDepartmentController")
public class EmployeeDepartmentController extends ResponseCodeController {
    private static Logger log = LoggerFactory.getLogger(EmployeeDepartmentController.class);

    @Autowired
    private EmployeeDepartmentService employeeDepartmentService;

    @RequestMapping(value = "", method = {RequestMethod.GET})
    @ResponseBody
    public RestResponse list() {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.getTreeRoot()
            );
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("list employee department error [{}]", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[{}]", response);
        }
        return response;
    }

    @RequestMapping(value = "/add", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public RestResponse addDepartment(
            @ModelAttribute EmployeeDepartmentModel department
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.add(department.convertToEmployeeDepartment())
            );
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("add employee department error [{}]", department, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[{},{}]", department, response);
        }
        return response;
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public RestResponse updateDepartment(
            @ModelAttribute EmployeeDepartment department
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.update(department)
            );
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("update employee department error [{}]", department, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[{},{}]", department, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse deleteById(
            @PathVariable("id") String id
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.deleteById(id));
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("delete employee department error [{}]", id, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[id:{},{}]", id, response);
        }
        return response;
    }

    @RequestMapping(value = "/addemp", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public RestResponse addEmployee(
            @ModelAttribute EmployeeDepartmentModel departmentModel,
            @ModelAttribute EmployeeModel employeeModel
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.addEmployee(
                            departmentModel.convertToEmployeeDepartment(),
                            employeeModel.convert()));
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("add employee error [dpt:{}, emp:{}]", departmentModel, employeeModel, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[dpt:{}, emp:{}, resp: {}]", departmentModel, employeeModel, response);
        }
        return response;
    }

}
