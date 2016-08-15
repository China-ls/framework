package com.infinite.water.controller;

import com.infinite.water.controller.resp.RestResponse;
import com.infinite.water.core.controller.AbstractController;
import com.infinite.water.core.util.IoUtils;
import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.Department;
import com.infinite.water.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/u")
@Controller("UserController")
public class UserController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/app";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUserInfo() {
        return new User("admin", "admin", "password");
    }

    @RequestMapping(value = "/department", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse department() {
        org.springframework.core.io.Resource resource = new ClassPathResource("xdepartment.json");
        RestResponse response = new RestResponse();
        try {
            String text = IoUtils.read(resource.getInputStream());
            response.setData(JsonUtil.fromJson(text, Department.class));
            response.setCode("0000");
            response.setMessage("success");
        } catch (IOException e) {
            log.debug("read xdepartment.json error: {}", e.getCause());
            response.setCode("0001");
            response.setMessage("fail");
        }
        return response;
    }




}
