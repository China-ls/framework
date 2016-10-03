package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeDuty;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.EmployeeService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hx on 16-7-6.
 * @since 1.0
 */
@RequestMapping(value = "/employee")
@RestController("EmployeeController")
public class EmployeeController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Response list(@ModelAttribute String page, @ModelAttribute String size) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    employeeService.listPager(NumberUtils.toInt(page), NumberUtils.toInt(size))
            );
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("list employee error. [employee: {}]", page, size, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("==[page:{},size:{},resp:{}]==", page, size, response);
        }
        return response;
    }

    @RequestMapping(value = "/add", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response addEmployee(@ModelAttribute Employee employee) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS, employeeService.addEmployee(employee));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("add employee error. [employee: {}]", employee, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("==[employee:{},resp:{}]==", employee, response);
        }
        return response;
    }

    @RequestMapping(value = "/del", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response delEmployee(@ModelAttribute("id") String id) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    employeeService.removeEmployee(id));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("remove employee error. [id: {}]", id, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("==[employee:{},resp:{}]==", id, response);
        }
        return response;
    }


    @RequestMapping(value = "/{id}/duty/add", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response addEmployeeDuty(@PathVariable("id") String id,
                                    @ModelAttribute EmployeeDuty employeeDuty) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    employeeService.setEmployeeDuty(id, employeeDuty));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("add employee error. [id: {}, duty: {}]", id, employeeDuty, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("==[id: {}, duty:{},resp:{}]==", id, employeeDuty, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/duty/remove", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response removeEmployeeDuty(
            @PathVariable("id") String id,
            @ModelAttribute("duty_id") String dutyId) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    employeeService.removeEmployeeDuty(id, dutyId));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("add employee error. [id: {}, duty: {}]", id, dutyId, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("==[id: {}, duty:{},resp:{}]==", id, dutyId, response);
        }
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}