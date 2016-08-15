package com.infinite.water.controller;

import com.infinite.water.core.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hx on 16-7-29.
 */
@Controller
public class RouteController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(RouteController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.jsp");
        return mav;
    }

    @RequestMapping(value = "/app", method = RequestMethod.GET)
    public ModelAndView app() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", "admin");
        mav.setViewName("index.jsp");
        return mav;
    }

    @RequestMapping("temp/department")
    @ResponseBody
    public String department() {
        return "";
    }

    @RequestMapping(value = "/tpl/**", method = RequestMethod.GET)
    public String template(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }

}
