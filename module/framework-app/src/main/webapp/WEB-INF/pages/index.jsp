<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String absCtx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); %>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//IETF//DTD LEVEL1//EN">
<!DOCTYPE html>
<html lang="en" data-ng-app="app">
<head>
    <meta charset="utf-8"/>
    <title>智能环保运营系统</title>
    <meta name="description" content=""/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href="${ctx}/img/logo.png" rel="shortcut icon" />

    <link rel="stylesheet" href="${ctx}/css/app.201610190959.css" type="text/css"/>

</head>
<body ng-controller="AppCtrl">
<input type="hidden" id="__ctx" value="${ctx}"/>
<!-- toaster directive -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true}"></toaster-container>
<!-- / toaster directive -->

<div class="app" id="app"
     ng-class="{'app-header-fixed':app.settings.headerFixed, 'app-aside-fixed':app.settings.asideFixed, 'app-aside-folded':app.settings.asideFolded, 'app-aside-hide': app.settings.asideHide, 'app-aside-dock':app.settings.asideDock, 'container':app.settings.container}"
     ui-view>
</div>
<!-- jQuery -->
<script src="${ctx}/js/app.201610190959.js"></script>
<!-- Lazy loading -->
</body>
</html>