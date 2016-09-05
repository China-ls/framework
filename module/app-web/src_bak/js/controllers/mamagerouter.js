'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageRouterCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '人员列表';
        $scope.app.subHeader.contentTitle = '人员数据';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;

        $scope.routeList = [{},{},{},{}];
    }]
);