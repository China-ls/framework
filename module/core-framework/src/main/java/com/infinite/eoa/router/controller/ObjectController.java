package com.infinite.eoa.router.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.PersistentObjectService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.Document;
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

import java.util.ArrayList;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/obj")
@RestController("ObjectController")
public class ObjectController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(ObjectController.class);

    @Autowired
    private PersistentObjectService persistentObjectService;

    @RequestMapping(value = "", method = {RequestMethod.GET})
    @ResponseBody
    public Response get(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @ModelAttribute("filter") String filter,
            @ModelAttribute("skip") String skip,
            @ModelAttribute("limit") String limit,
            @ModelAttribute("include") ArrayList<String> include,
            @ModelAttribute("exclude") ArrayList<String> exclude,
            @ModelAttribute("asc") ArrayList<String> sortAsc,
            @ModelAttribute("dsc") ArrayList<String> sortDsc
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.get(
                                appkey, namespace,
                                StringUtils.isEmpty(filter) ? null : Document.parse(filter),
                                NumberUtils.toInt(skip), NumberUtils.toInt(limit),
                                include, exclude, sortAsc, sortDsc
                        )
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get from persistent.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}, skip:{}, limit:{}, include:{}, exclude:{}, asc:{}, dsc:{}]" +
                    "resp:{}", appkey, namespace, filter, skip, limit, include, exclude, sortAsc, sortDsc, response);
        }
        return response;
    }

    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ResponseBody
    public Response put(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @ModelAttribute("data") String data
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.put(appkey, namespace, Document.parse(data))
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error put into persistent.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, data:{}] resp:{}", appkey, namespace, data, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/update", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response updateById(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @PathVariable("id") String id,
            @ModelAttribute("filter") String filter,
            @ModelAttribute("updates") String updates
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id) || StringUtils.isEmpty(updates)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.updateOne(appkey, namespace, id,
                                StringUtils.isEmpty(filter) ? null : Document.parse(filter),
                                Document.parse(updates))
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}, updates:{}] resp:{}", appkey, namespace, filter, updates, response);
        }
        return response;
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response updateOne(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @ModelAttribute("filter") String filter,
            @ModelAttribute("updates") String updates
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(filter) || StringUtils.isEmpty(updates)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.update(appkey, namespace,
                                Document.parse(filter),
                                Document.parse(updates))
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}, updates:{}] resp:{}", appkey, namespace, filter, updates, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    public Response deleteById(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @PathVariable("id") String id
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.deleteOne(appkey, namespace, id)
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, id:{}] resp:{}", appkey, namespace, id, response);
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    public Response deleteByFilter(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @ModelAttribute("filter") String filter
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.delete(appkey, namespace, Document.parse(filter))
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}] resp:{}", appkey, namespace, filter, response);
        }
        return response;
    }

    @RequestMapping(value = "/delete/bulk/one", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ResponseBody
    public Response deleteOneBulk(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") ArrayList<String> namespace,
            @ModelAttribute("filter") String filterStr
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(filterStr)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                JsonArray filters = JsonUtil.fromJson(filterStr, JsonArray.class);
                ArrayList<Document> filterDocuments = new ArrayList<Document>();
                for (JsonElement filter : filters) {
                    filterDocuments.add(Document.parse(filter.toString()));
                }
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.deleteOneBulk(appkey, namespace, filterDocuments)
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}] resp:{}", appkey, namespace, filterStr, response);
        }
        return response;
    }

    @RequestMapping(value = "/delete/bulk/many", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ResponseBody
    public Response deleteManyBulk(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") ArrayList<String> namespace,
            @ModelAttribute("filter") String filterStr
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(filterStr)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                JsonArray filters = JsonUtil.fromJson(filterStr, JsonArray.class);
                ArrayList<Document> filterDocuments = new ArrayList<Document>();
                for (JsonElement filter : filters) {
                    filterDocuments.add(Document.parse(filter.toString()));
                }
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.deleteManyBulk(appkey, namespace, filterDocuments)
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (JsonSyntaxException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY, "filter");
            if (log.isDebugEnabled()) {
                log.debug("filter[{}] format error.", filterStr, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error updates.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, filter:{}] resp:{}", appkey, namespace, filterStr, response);
        }
        return response;
    }

    @RequestMapping(value = "/replace", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response replace(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") String namespace,
            @ModelAttribute("id") String id,
            @ModelAttribute("data") String data
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.replace(appkey, namespace, id, Document.parse(data))
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error put into persistent.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, data:{}] resp:{}", appkey, namespace, data, response);
        }
        return response;
    }

    @RequestMapping(value = "/bulk", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response bulk(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("namespace") ArrayList<String> namespace,
            @ModelAttribute("bulk") String bulk
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        persistentObjectService.bulk(appkey, namespace, bulk)
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
            if (log.isDebugEnabled()) {
                log.debug("APPKEY[{}] not exsist.", appkey, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error bulk.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, namespace:{}, bulk:{}] resp:{}", appkey, namespace, bulk, response);
        }
        return response;
    }

}
