<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//IETF//DTD LEVEL1//EN">
<!DOCTYPE html>
<html lang="en" data-ng-app="app">
<head>
    <meta charset="utf-8"/>
    <title>登录</title>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="stylesheet" href="${ctx}/css/app.login.css" type="text/css"/>
</head>
<body ng-controller="AppCtrl">

<div>
    <div class="login-wrapper">

        <div class="login-box">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                            <div class="form-top">
                                <div class="form-top-left pull-left">
                                    <h3>登录</h3>
                                </div>
                                <div class="form-top-right pull-right">
                                    <i class="fa fa-lock"></i>
                                </div>
                            </div>
                            <div class="form-bottom">
                                <form role="form" action="${ctx}/u/login" method="post" class="login-form">
                                    <div class="form-group">
                                        <label class="sr-only" for="form-username">用户名</label>
                                        <input type="text" name="form-username" placeholder="用户名..."
                                               class="form-username form-control" id="form-username">
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" for="form-password">密码</label>
                                        <input type="password" id="form-password" name="form-password"
                                               class="form-password form-control" placeholder="密码...">
                                    </div>
                                    <button type="submit" class="btn">登录</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="backstretch"></div>

    </div>
</div>

</body>
</html>
