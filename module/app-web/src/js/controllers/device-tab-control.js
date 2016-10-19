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

            if (!$scope.sensor) {
                $scope.sensor = {online: 2};
            }

            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_BY_ID + $scope.$stateParams.id)
                .then(function (response) {
                    try {
                        // console.warn(response);
                        $scope.object = response.data.data;
                        $scope.sensor = $scope.object.sensor;
                        $scope.mc = $scope.object.mc;
                        $scope.cc = $scope.object.cc;
                        $scope.sc = $scope.object.sc;
                        $scope.classifyData($scope.object.data);
                    } catch (e) {
                        console.warn(e);
                    }
                });

            $scope.classifyData = function (data) {
                if (!data) {
                    return;
                }
                var dm = {};
                if (data) {
                    angular.forEach(data, function (item) {
                        if (item.time) {
                            item.time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                        }
                        dm[item.comp_id] = item;
                    });
                }

                if ($scope.mc) {
                    $scope.classifyDataByItem($scope.object.mc, dm[$scope.object.mc.comp_id]);
                }
                if ($scope.cc) {
                    angular.forEach($scope.object.cc, function (item) {
                        $scope.classifyDataByItem(item, dm[item.comp_id]);
                    });
                }
                if ($scope.sc) {
                    angular.forEach($scope.object.sc, function (item) {
                        $scope.classifyDataByItem(item, dm[item.comp_id]);
                    });
                }
            };

            $scope.classifyDataByItem = function (item, data) {
                item.data = data;
                item.label = '';
                item.onoff = !data ? false : data.onoff;
                item.discontrol = !data || $scope.sensor.online !== 1;
            };

            $scope.onControl = function (comp) {
                if ($scope.sensor.online != 1 || comp.data && comp.onoff === comp.data.onoff) {
                    return;
                }
                comp.label = '<i class="fa fa-spinner fa-spin"></i>';
                comp.discontrol = true;
                var val = comp.onoff ? 1 : 0;
                $scope.wsSend('{"channel_id" : "' + comp.comp_id + '","operation" : "switch","param" : ' + val + ',"sensor_id" : "' + $scope.sensor.sensor_id + '"}');
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