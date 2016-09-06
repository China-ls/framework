package com.infinite.water.controller;

import com.infinite.water.beans.propertyeditors.NumberDateEditor;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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
    public RestResponse listDepartment() {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.getDepartmentTreeRoot()
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
                    employeeDepartmentService.addDepartment(department.convertToEmployeeDepartment())
            );
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("addDepartment employee department error [{}]", department, e);
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
                    employeeDepartmentService.updateDepartment(department)
            );
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("updateDepartment employee department error [{}]", department, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[{},{}]", department, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse deleteDepartmentById(
            @PathVariable("id") String id
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.deleteDepartmentById(id));
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
            EmployeeDepartment department = departmentModel.convertToEmployeeDepartment();
            EmployeeModel employee = employeeModel.convert();
            employee.setDepartmentId(department.getIdHexString());
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.addEmployee(department, employee));
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("addDepartment employee error [dpt:{}, emp:{}]", departmentModel, employeeModel, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[dpt:{}, emp:{}, resp: {}]", departmentModel, employeeModel, response);
        }
        return response;
    }

    @RequestMapping(value = "/delemp/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse deleteEmployeeById(
            @PathVariable("id") String id
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    employeeDepartmentService.deleteEmployeeById(id));
        } catch (Throwable e) {
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("delete employee error [{}]", id, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[id:{},{}]", id, response);
        }
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new NumberDateEditor(true));
    }
}
