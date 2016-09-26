package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.DepartmentModel;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.DepartmentService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hx on 16-7-6.
 * @since 1.0
 */
@RequestMapping(value = "/department")
@RestController("DepartmentController")
public class DepartmentController extends BasicRestController {
    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getDepartmentByType(@ModelAttribute("type") String type) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS, departmentService.listByType(type));
        } catch (Throwable e) {
            logger.debug("get department by [type:{}] error.", type, e);
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("==[type:{},resp:{}]==", type, response);
        }
        return response;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Response listDepartment(
            @ModelAttribute("page") String page,
            @ModelAttribute("size") String size
    ) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    departmentService.listPagerAll(NumberUtils.toInt(page), NumberUtils.toInt(size), false)
            );
        } catch (Throwable e) {
            logger.debug("list {} department error.", page, size, e);
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("==[page:{},size:{},resp:{}]==", page, size, response);
        }
        return response;
    }

    @RequestMapping(value = "/add", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response addDepartment(@ModelAttribute DepartmentModel model) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    departmentService.save(model.convertAdd())
            );
        } catch (Throwable e) {
            logger.debug("add department [{}] error.", model, e);
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("==[model:{},resp:{}]==", model, response);
        }
        return response;
    }

}