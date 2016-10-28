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

            $scope.isShowingOnBoard = false;

            $scope.onChangeShow = function () {
                if (!$scope.mc || $scope.mc.discontrol || !$scope.mc.onoff) {
                    return;
                }
                $scope.isShowingOnBoard = !$scope.isShowingOnBoard;
                var val = $scope.isShowingOnBoard ? 2 : 1;
                $scope.wsSend('{"operation" : "set_control_mode","param" : ' + val + ',"sensor_id" : "' + $scope.sensor.sensor_id + '"}');
            };

            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_BY_ID + $scope.$stateParams.id)
                .then(function (response) {
                    try {
                        // console.warn(response);
                        $scope.object = response.data.data;
                        $scope.sensor = $scope.object.sensor;
                        $scope.mc = $scope.object.mc;
                        $scope.cc = $scope.object.cc;
                        $scope.sc = $scope.object.sc;
                        $scope.oc = $scope.object.oc;
                        $scope.classifyData($scope.object.data, true);
                    } catch (e) {
                        console.warn(e);
                    }
                });

            $scope.classifyData = function (data, first) {
                first = first || false;
                if (!data) {
                    return;
                }
                var dm = {};
                if (data) {
                    angular.forEach(data, function (item) {
                        if (item.time) {
                            item.display_time = $scope.formatDate(new Date(item.time), "yyyy年MM月dd日HH:mm:ss");
                        }
                        dm[item.comp_id] = item;
                    });
                    if (data[0]) {
                        $scope.sensor.update_time = data[0].display_time;
                    }
                }

                if ($scope.mc) {
                    $scope.classifyDataByItem($scope.mc, dm[$scope.mc.comp_id]);
                }
                if ($scope.cc) {
                    angular.forEach($scope.cc, function (item) {
                        var ccdata = dm[item.comp_id];
                        // console.warn(ccdata);
                        if (first) {
                            $scope.isShowingOnBoard = !ccdata || !ccdata.status;
                        }
                        $scope.classifyDataByItem(item, ccdata);
                    });
                } else {
                    if (first) {
                        $scope.isShowingOnBoard = false;
                    }
                }
                if ($scope.sc) {
                    angular.forEach($scope.sc, function (item) {
                        $scope.classifyDataByItem(item, dm[item.comp_id]);
                    });
                }
                if ($scope.oc) {
                    angular.forEach($scope.oc, function (item) {
                        $scope.classifyDataByItem(item, dm[item.comp_id]);
                    });
                }
            };

            $scope.classifyDataByItem = function (item, data) {
                item.data = data;
                item.label = false;
                item.onoff = !data ? false : data.onoff;
                item.discontrol = !data || $scope.sensor.online !== 1;
                if ($scope.object.cwtc) {
                    item.cwtc = {
                        month: $scope.object.cwtc.month[item.comp_id],
                        day: $scope.object.cwtc.day[item.comp_id]
                    };
                }
            };

            $scope.onControl = function (comp) {
                if ($scope.sensor.online != 1 || (comp != $scope.mc && !$scope.mc.onoff) || !comp || !comp.data || comp.discontrol || comp.onoff != comp.data.onoff) {
                    return;
                }
                // console.warn('call ');
                comp.discontrol = true;
                comp.onoff = !comp.onoff;
                comp.label = true;
                var val = comp.onoff ? 1 : 0;
                var text = '{"channel_id" : "' + comp.comp_id + '","operation" : "switch","param" : ' + val + ',"sensor_id" : "' + $scope.sensor.sensor_id + '"}';
                // console.error(text);
                $scope.wsSend(text);
            };

            $scope.$on("WS_MESSAGE", function (event, data) {
                // console.warn(data);
                try {
                    if (!data || data[0].sensor_id !== $scope.sensor.sensor_id) {
                        return;
                    }
                    $scope.classifyData(data);
                } catch (e) {
                    console.error(e);
                }
            });
        }])
;