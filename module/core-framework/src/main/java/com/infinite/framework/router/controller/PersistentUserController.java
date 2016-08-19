package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.core.web.entity.Response;
import com.infinite.framework.entity.PersistentUser;
import com.infinite.framework.router.entity.ResponseCode;
import com.infinite.framework.service.PersistentUserService;
import com.infinite.framework.service.exception.ApplcationNotExsistException;
import com.infinite.framework.service.exception.InvalidDataException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/persistent/user")
@RestController("PersistentUserController")
public class PersistentUserController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(PersistentUserController.class);

    @Autowired
    private PersistentUserService persistentUserService;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Response create(@RequestHeader("APPKEY") String appkey, @ModelAttribute PersistentUser user) {
        Response response = null;
        if (log.isDebugEnabled()) {
            log.debug("create persistent user", appkey, user);
        }
        try {
            if (null == user || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, persistentUserService.save(appkey, user));
            }
        } catch (InvalidDataException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (ApplcationNotExsistException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public PersistentUser get(@RequestHeader("APPKEY") String appkey, @PathVariable("id") String id) {
        return persistentUserService.findById(appkey, id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentUser update(@RequestHeader("APPKEY") String appkey, @PathVariable("id") String id) {
        return persistentUserService.findById(appkey, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public PersistentUser delete(@RequestHeader("APPKEY") String appkey, @PathVariable("id") String id) {
        return persistentUserService.findById(appkey, id);
    }

}
