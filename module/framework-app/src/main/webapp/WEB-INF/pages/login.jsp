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

    <link rel="stylesheet" href="${ctx}/css/app.201610210923.css" type="text/css"/>
</head>
<body ng-controller="LoginCtrl" class="login-body">

<!--<input type="hidden" id="__ctx" value="http://120.55.165.5:9998/app"/>-->
<input type="hidden" id="__ctx" value="${ctx}"/>

<!-- toaster directive -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true}"></toaster-container>
<!-- / toaster directive -->


<div class="login" id="login">

    <div class="login-header">
        <img class="login-title" src="${ctx}/img/u40.png" width="440" height="48">
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
            <form class="wrapper bg-light-gray1-only b b-gray form form-validation">
                <div class="m-b-xs text-center wrapper-sm" ng-class="{'show-login-result': null != loginResult}">
                    <!--<div class="m-b-xs text-center wrapper-sm">-->
                    <span class="login-form-title"><b>用户登录</b></span>
                    <alert class="login-result-alert m-b-none m-t-xs" type="danger" close="clearLoginResult()">
                        登录失败: <span>{{loginResult.message}}</span>
                    </alert>
                </div>

                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-envelope"></i>
                        </span>
                        <input ng-model="user.username" type="text" class="form-control"
                               placeholder="Email/用户名/手机号" value="admin" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group m-b">
                        <span class="input-group-addon">
                            <i class="icon-lock"></i>
                        </span>
                        <input ng-model="user.password" type="password"
                               class="form-control" placeholder="密码" value="password" required>
                    </div>
                </div>
                <div class="form-group">
                    <b>
                        <button type="button" class="img-full btn btn-info"
                                ng-click="submit()" ng-disabled="loginPromise">登录
                            <i class="fa fa-spinner fa-spin hide" ng-class="{'hide': !loginPromise}"></i>
                        </button>
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
    angular.module('login', ["toaster", 'ui.bootstrap'])
            .config(['$httpProvider', function ($httpProvider) {
                $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
                $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

                // Override $http service's default transformRequest
                $httpProvider.defaults.transformRequest = [function (data) {
                    var param = function (data) {
                        var query = '';
                        var name, value, fullSubName, subName, subValue, innerObj, i;

                        for (name in data) {
                            value = data[name];

                            if (value instanceof Array) {
                                for (i = 0; i < value.length; ++i) {
                                    subValue = value[i];
                                    fullSubName = name + '[' + i + ']';
                                    innerObj = {};
                                    innerObj[fullSubName] = subValue;
                                    query += param(innerObj) + '&';
                                }
                            } else if (value instanceof Object) {
                                for (subName in value) {
                                    subValue = value[subName];
                                    fullSubName = name + '[' + subName + ']';
                                    innerObj = {};
                                    innerObj[fullSubName] = subValue;
                                    query += param(innerObj) + '&';
                                }
                            } else if (value !== undefined && value !== null) {
                                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
                            }
                        }
                        return query.length ? query.substr(0, query.length - 1) : query;
                    };

                    return angular.isObject(data) && String(data) !== '[object File]'
                            ? param(data)
                            : data;
                }];
            }])
            .controller('LoginCtrl', ['$scope', 'toaster', '$http', '$window',
                function ($scope, toaster, $http, $window) {
                    $scope.CTX = angular.element("#__ctx").val();
                    $scope.imageListSlideInterval = 5000;
                    $scope.imageList = [
                        'img/u9.png',
                        'img/u7.png',
                        'img/u5.png'
                    ];
                    $scope.loginPromise = false;
                    $scope.toastWarn = function (text) {
                        toaster.pop('warning', '提示', text);
                    };
                    $scope.toastError = function (text) {
                        toaster.pop('error', '警告', text);
                    };
                    if (!$scope.user) {
                        $scope.user = {
                            username: 'admin',
                            password: 'admin'
                        };
                    }
                    $scope.clearLoginResult = function () {
                        $scope.loginResult = null;
                    };
                    $scope.submit = function () {
                        if (!$scope.user.username) {
                            $scope.toastWarn('请输入用户名');
                            return;
                        }
                        if (!$scope.user.password) {
                            $scope.toastWarn('请输入密码');
                            return;
                        }
                        $scope.loginPromise = true;
//                        console.warn($scope.user);
                        $scope.loginResult = null;
                        $http.post($scope.CTX + '/login', $scope.user).then(function (response) {
                            if (response.data.code === '0') {
                                $window.location.href = $scope.CTX + '/app';
                            } else {
                                $scope.loginResult = response.data;
                            }
//                            console.warn(response);
                            $scope.loginPromise = false;
                        }, function (response) {
                            $scope.toastError('服务器响应异常，请联系管理员。');
                            $scope.loginPromise = false;
                        });
                    };
                }])
    ;
</script>
</html>
