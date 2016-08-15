package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/obj")
@RestController("ObjectController")
public class ObjectController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(ObjectController.class);



}
