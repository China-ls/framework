'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageDeviceCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '设备列表';
        $scope.app.subHeader.contentTitle = '设备数据';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;
    }]
);