'use strict';

// DeviceDetail controller
app.controller('DeviceTabInfoCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        if (!$scope.$stateParams) {
            $state.go('app.device');
            return;
        }

        $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_BY_ID + $scope.$stateParams.id)
            .then(function (response) {
                try {
                    // console.warn(response);
                    $scope.object = response.data.data;
                    $scope.sensor = $scope.object.sensor;
                    if ($scope.sensor && $scope.sensor.components) {
                        // console.warn($scope.$stateParams);
                        $scope.app.subHeader.contentTitle = $scope.sensor.name;
                        if ($scope.object.data) {
                            angular.forEach($scope.object.data, function (item) {
                                if (item.comp_type === 'flowmeter_sensor') {
                                    item.time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                                    if (!item.instant) {
                                        item.instant = "0";
                                    }
                                    $scope.sensor.flowmeter_data = item;
                                }
                            });
                        }
                        $scope.sensor.version = $scope.sensor.version / 10.0;
                        $scope.sensor.dsplay_status =
                            ($scope.sensor.status === 'NORMAL') ? "正常" : "异常";
                    }
                } catch (e) {
                    console.warn(e);
                }
            });

        $scope.$on("WS_MESSAGE", function (event, data) {
            // console.warn(data);
            try {
                angular.forEach(data, function (item) {
                    if (item.sensor_id != $scope.device.sensor.sensor_id) {
                        return;
                    }
                    if (item.comp_type === 'flowmeter_sensor') {
                        item.time = $scope.formatDate(new Date(/*item.time*/), "yyyy年MM月dd日HH:mm:ss");
                        if (!item.instant) {
                            item.instant = "0";
                        }
                        $scope.device.sensor.flowmeter_data = item;
                    }
                });
            } catch (e) {
            }
        });
    }])
;