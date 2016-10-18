package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Controller("RouterController")
public class RouterController extends BasicRestController {
    private static Logger LOG = LoggerFactory.getLogger(RouterController.class);


    @Autowired
    private ServletContext servletContext;

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
            path = path.substring(1);
        }
        return path;
    }

    /*@RequestMapping(value = "/js*//**", method = RequestMethod.GET)
     @ResponseBody public void js(HttpServletRequest request, HttpServletResponse response) {
     outputResourceFileByServletRealPath(request, response);
     }

     @RequestMapping(value = "/css*//**", method = RequestMethod.GET)
     @ResponseBody public void css(HttpServletRequest request, HttpServletResponse response) {
     outputResourceFileByServletRealPath(request, response);
     }

     @RequestMapping(value = "/lib*//**", method = RequestMethod.GET)
     @ResponseBody public void lib(HttpServletRequest request, HttpServletResponse response) {
     outputResourceFileByServletRealPath(request, response);
     }

     @RequestMapping(value = "/tpl*//**", method = RequestMethod.GET)
     @ResponseBody public void template(HttpServletRequest request, HttpServletResponse response) {
     outputResourceFileByServletRealPath(request, response, "/WEB-INF/pages");
     }

     private void outputResourceFileByServletRealPath(HttpServletRequest request, HttpServletResponse response) {
     outputResourceFileByServletRealPath(request, response, null);
     }

     private void outputResourceFileByServletRealPath(HttpServletRequest request, HttpServletResponse response, String prefix) {
     if (null == prefix) {
     prefix = "";
     }
     String path = request.getServletPath();
     if (path.startsWith("/")) {
     path = prefix + path;
     } else {
     path = prefix + "/" + path;
     }
     String realPath = servletContext.getRealPath(path);
     FileInputStream fis = null;
     InputStreamReader isr = null;
     try {
     fis = new FileInputStream(realPath);
     isr = new InputStreamReader(fis, "UTF-8");
     IOUtils.copy(isr, response.getOutputStream());
     } catch (FileNotFoundException e) {
     if (LOG.isWarnEnabled()) {
     LOG.warn("java.io.FileNotFoundException : {}", path);
     }
     } catch (IOException e) {
     if (LOG.isWarnEnabled()) {
     LOG.warn("java.io.IOException : {}", e.getMessage());
     }
     } finally {
     IOUtils.closeQuietly(fis);
     IOUtils.closeQuietly(isr);
     }
     }*/
}
