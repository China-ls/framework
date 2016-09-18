<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//IETF//DTD LEVEL1//EN">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>登录</title>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="stylesheet" href="${ctx}/css/app.min.css" type="text/css"/>
</head>
<body class="app-login-body">

<div class="app-login">

    <div class="row">

        <div class="container login-form">
            <form class="col-sm-offset-3 col-sm-6 wrapper bg-light-gray1-only b b-gray">
                <div class="m-b-xs">
                    <span class="m">
                        <img class="m-b-sm" src="img/login_logo.png" width="66" height="28"/>
                    </span>
                    <span class="login-form-title">智能环保运营平台</span>
                </div>
                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-envelope"></i>
                        </span>
                        <input type="text" class="form-control" placeholder="Email/用户名/手机号">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-lock"></i>
                        </span>
                        <input type="password" class="form-control" placeholder="密码">
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="img-full btn btn-info btn-lg">登录</button>
                </div>
                <div class="form-group">
                    <span class="powered-by">Powered by Infinite Tech</span>
                    <span class="pull-right">
                        <div class="checkbox">
                            <label class="i-checks">
                                <input type="checkbox"><i></i> 记住密码
                            </label>
                        </div>
                    </span>
                </div>
            </form>
        </div>

    </div>
</div>

</body>
</html>
