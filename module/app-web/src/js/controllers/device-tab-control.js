'use strict';

// DeviceDetail controller
app.controller('DeviceTabControlCtrl',
    ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
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
                        console.warn(response);
                        $scope.object = response.data.data;
                        $scope.sensor = $scope.object.sensor;
                        var data = $scope.object.data;
                        var dm = {};
                        if (data) {
                            angular.forEach(data, function (item) {
                                if (item.time) {
                                    item.time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                                }
                                dm[item.comp_id] = item;
                            });
                        }
                        if ($scope.sensor && $scope.sensor.components) {
                            for (var i = 0; i < $scope.sensor.components.length; i++) {
                                $scope.sensor.components[i].data = dm[$scope.sensor.components[i].comp_id];
                                $scope.sensor.components[i].isControl = typeof $scope.sensor.components[i].data.onoff != 'undefined';
                                $scope.sensor.components[i].onoff = $scope.sensor.components[i].data.onoff;
                                $scope.sensor.components[i].discontrol = $scope.sensor.components[i].status != 'NORMAL';
                            }
                        }
                    } catch (e) {
                        console.warn(e);
                    }
                });

            $scope.onControl = function (comp) {
                if (comp.data && comp.onoff === comp.data.onoff) {
                    return;
                }
                comp.label = '<i class="fa fa-spinner fa-spin"></i>';
                comp.discontrol = true;
                $scope.wsSend('{"channel_id" : "' + comp.comp_id + '","operation" : "switch","param" : ' + comp.onoff + ',"sensor_id" : "' + $scope.sensor.sensor_id + '"}');
            };

            $scope.$on("WS_MESSAGE", function (event, data) {
                try {
                    if (data) {
                        var dm = {};
                        angular.forEach(data, function (item) {
                            if (item.sensor_id != $scope.sensor.sensor_id) {
                                return;
                            }
                            if (item.time) {
                                item.time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                            }
                            dm[item.comp_id] = item;
                        });
                        for (var i = 0; i < $scope.sensor.components.length; i++) {
                            $scope.sensor.components[i].data = dm[$scope.sensor.components[i].comp_id];
                            if ($scope.sensor.components[i].data) {
                                $scope.sensor.components[i].isControl = typeof $scope.sensor.components[i].data.onoff != 'undefined';
                                $scope.sensor.components[i].onoff = $scope.sensor.components[i].data.onoff;
                                $scope.sensor.components[i].discontrol = $scope.sensor.components[i].status != 'NORMAL';
                                $scope.sensor.components[i].label = '';
                                // } else {
                                // console.warn(comp);
                            }
                        }
                    }
                } catch (e) {
                    console.error(e);
                }
            });
        }])
;