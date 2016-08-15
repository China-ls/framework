package com.infinite.water.core.controller;

public class AbstractController {

    public static String getPathRelativeToRoot(String path) {
        return "/WEB-INF/app-web/src/" + path;
    }

}
