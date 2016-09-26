package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("RouterController")
public class RouterController extends BasicRestController {

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

    @RequestMapping(value = "/tpl/**", method = RequestMethod.GET)
    public String template(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }
}
