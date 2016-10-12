package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.Role;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.router.entity.RoleModel;
import com.infinite.eoa.service.PermissionService;
import com.infinite.eoa.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/role")
@RestController
public class RoleController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response update(RoleModel roleModel) {
        Response response = null;
        try {
//            log.debug("id: {} : {}", roleModel.getId(), roleModel.getId().getClass());
            Role role = roleModel.convertAdd();
            role.setId(roleModel.getId());
            response = makeResponse(ResponseCode.SUCCESS,
                    roleService.updateRole(role, StringUtils.split(roleModel.getPks(), ","))
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("update role [{}].", roleModel, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response add(RoleModel role) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    roleService.addRole(role.convertAdd(), StringUtils.split(role.getPks(), ","))
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("add role [{}].", role, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/del", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response del(@ModelAttribute("id") String id) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    roleService.removeRole(id)
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("remove role [{}].", id, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Response list() {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS, roleService.listAll());
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get from persistent.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/permission", method = {RequestMethod.GET})
    @ResponseBody
    public Response getRolePermission(
            @ModelAttribute("id") String roleid
    ) {
        Response response = null;
        try {
            Document document = new Document("id", roleid);
            document.append("keys", roleService.getPermissionKeyListByRoleId(roleid));
            document.append("permissions", permissionService.findAll());

            response = makeResponse(ResponseCode.SUCCESS, document);
        } catch (IllegalArgumentException e) {
            response = makeResponse(ResponseCode.PARAM_EMPTY);
            if (log.isErrorEnabled()) {
                log.error("{}", roleid, e);
            }
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("{}", roleid, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/permissionkey", method = {RequestMethod.GET})
    @ResponseBody
    public Response getRolePermissionKeys(
            @ModelAttribute("id") String roleid
    ) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    roleService.getPermissionKeyListByRoleId(roleid)
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get from persistent.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

}
