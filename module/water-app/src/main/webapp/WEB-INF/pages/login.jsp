<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//IETF//DTD LEVEL1//EN">
<!DOCTYPE html>
<html lang="en" data-ng-app="login">
<head>
    <meta charset="utf-8"/>
    <title>登录</title>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href="${ctx}/img/logo.png" rel="shortcut icon"/>

    <link rel="stylesheet" href="${ctx}/css/app.201609191258.css" type="text/css"/>
</head>
<body ng-controller="LoginCtrl" class="login-body">

<!-- toaster directive -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true}"></toaster-container>
<!-- / toaster directive -->


<div class="login" id="login">

    <div class="login-header">
        <img class="login-title" src="${ctx}/img/u40.png" width="500" height="48">
    </div>

    <!-- carousel -->
    <div class="login-carousel" set-ng-animate="false" style="height: 100%;background-color: #BCBCBC;display: block;">
        <carousel interval="imageListSlideInterval">
            <slide ng-repeat="row in imageList">
                <img ng-src="{{row}}" style="height: 100%;">
            </slide>
        </carousel>
    </div>
    <!-- / carousel -->

    <div class="login-wrapper">

        <div>
            <form class="wrapper bg-light-gray1-only b b-gray" action="${ctx}/u/login" method="post">
                <div class="m-b-xs text-center wrapper-sm">
                    <b><span class="login-form-title">用户登录</span></b>
                </div>
                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-envelope"></i>
                        </span>
                        <input type="text" class="form-control" placeholder="Email/用户名/手机号" value="admin">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-lock"></i>
                        </span>
                        <input type="password" class="form-control" placeholder="密码" value="password">
                    </div>
                </div>
                <div class="form-group">
                    <b>
                        <button type="submit" class="img-full btn btn-info">登录</button>
                    </b>
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

<div class="login-copy-right text-center padder-md-v">
    Copyright &copy; 2016 aidiman All Right Reserved
</div>

</body>

<!-- jQuery -->
<script src="${ctx}/lib/jquery/jquery.min.js"></script>
<!-- Angular -->
<script src="${ctx}/lib/angular/angular.min.js"></script>
<script src="${ctx}/lib/angular-bootstrap/ui-bootstrap.min.js"></script>
<script src="${ctx}/lib/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
<script src="${ctx}/lib/angularjs-toaster/toaster.min.js"></script>

<script type="text/javascript">
    angular.module('login', [
        "toaster",
        'ui.bootstrap'
    ]).controller('LoginCtrl', ['$scope', function ($scope) {
        $scope.imageListSlideInterval = 5000;
        $scope.imageList = [
            'img/u9.png',
            'img/u7.png',
            'img/u5.png'
        ];
    }])
    ;
</script>
</html>
