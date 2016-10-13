'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageDeviceModifyTitleCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.contentTitle = $localStorage.isUpdate ? '更新站点' : '新增站点';
        $scope.app.subHeader.goBackSref = 'app.mngdevice';
    }]
);

// ManageRouterCtrl controller
app.controller('ManageDeviceModifyCtrl', ['$scope', '$http', '$localStorage', '$state',
    function ($scope, $http, $localStorage, $state) {
        $scope.device = {
            longitude: 120.95281,
            latitude: 30.883874
        };
        $scope.mapOptions = {
            center: {
                longitude: 120.95281,
                latitude: 30.883874
            },
            zoom: 17,
            city: 'JiaShan',
            enableMapClick: false,
            clientHeight: $scope.baidumapeight,
            overlays: $scope.overlays
        };

        $scope.onMapClick = function ($event, $params) {
            if (!$params || !$params[0] || !$params[0].point) {
                return;
            }
            $scope.device.longitude = $params[0].point.lng;
            $scope.device.latitude = $params[0].point.lat;
            if (!$scope.marker) {
                return;
            }
            $scope.marker.setPosition($params[0].point);
        };
        $scope.onMapLoaded = function ($event, $params) {
            $scope.marker = new BMap.Marker(
                new BMap.Point($scope.device.longitude, $scope.device.latitude),
                {icon: new BMap.Icon("img/marker_blue.png", new BMap.Size(32, 32))}
            );
            $scope.myMap.addOverlay($scope.marker);
        };
        $scope.cancel = function () {
            $state.go('app.mngdevice');
        };
    }]
);