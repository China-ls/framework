package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.PersistentUser;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.PersistentUserService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import com.infinite.eoa.service.exception.InvalidDataException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

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
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == user || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, persistentUserService.save(appkey, user));
            }
        } catch (InvalidDataException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("put persistent user error!", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, user:{}, response:{}]", appkey, user, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Response get(@RequestHeader("APPKEY") String appkey, @PathVariable("id") String id) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, persistentUserService.findById(appkey, id));
            }
        } catch (InvalidDataException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("get persistent user error!", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]", appkey, user, response);
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestHeader("APPKEY") String appkey, @ModelAttribute PersistentUser user) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == user || StringUtils.isEmpty(user.getId())) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, persistentUserService.save(appkey, user));
            }
        } catch (InvalidDataException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]", appkey, user, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@RequestHeader("APPKEY") String appkey, @PathVariable("id") String[] ids) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (ArrayUtils.isEmpty(ids)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, persistentUserService.delete(appkey, ids));
            }
        } catch (InvalidDataException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]", appkey, user, response);
        }
        return response;
    }

}
