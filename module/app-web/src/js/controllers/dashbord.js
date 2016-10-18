'use strict';

/* Controllers */

// DashbordCtrl controller
app.controller('DeviceHeaderCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '设备列表';
        $scope.app.subHeader.contentTitle = '设备数据';
        $scope.app.subHeader.goBackHide = true;
    }]
);

app.controller('AsideDeviceCtrl', ['$window', '$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($window, $scope, $http, $localStorage, $state, APPCONST) {
        $scope.treeData = [];
        $scope.tree = {};
        $scope.dataListType = '用电量';

        $scope.analysDepartment = function (data, level) {
            var children = [];
            data.type = 'dpt';
            var color = 'c-red';
            if (level === 1) {
                color = 'c-yellow';
            } else if (level === 2) {
                color = 'c-green';
            } else if (level >= 3) {
                color = 'c-blue';
            }
            level++;
            data.icon = 'fa fa-flag ' + color;
            if (data.sub_departments) {
                data.sub_departments.forEach(function (item) {
                    children.push($scope.analysDepartment(item, level));
                });
            }
            if (data.devices) {
                data.devices.forEach(function (item) {
                    var child = $scope.analysDepartment(item, level);
                    child.type = 'device';
                    child.icon = 'fa fa-map-marker c-green';
                    children.push(child);
                });
            }
            data.children = children;
            data.label = data.name;
            return data;
        };

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT).then(function (response) {
            var data = $scope.analysDepartment(response.data.data, 0);
            $scope.treeData.push(data);
            try {
                $scope.tree.expand_all();
                $scope.tree.select_first_branch();
            } catch (e) {
            }
        }, function (response) {
        });

        $scope.treeItemClick = function (branch) {
            if (branch.type === 'dpt') {
                $state.go("app.device", {id: branch.uuid}, {inherit: false});
                $scope.$emit("EMIT_DEVICE_TREE_CLICK", branch);
                $scope.app.subHeader.contentTitle = branch.name;
                $scope.app.cache.selectParentBranch = null;
            } else if (branch.type === 'device') {
                $scope.app.cache.selectParentBranch = $scope.tree.get_parent_branch(branch);
                // console.warn($scope.tree.get_parent_branch(branch));
                $state.go("app.device.tab.info", {id: branch.uuid, device: branch});
            }
        };
    }]
);

app.controller('MapModalInstanceCtrl', ['$scope', '$modalInstance', 'device', function ($scope, $modalInstance, device) {
    $scope.device = device;
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);

app.controller('DashbordCtrl',
    ['$window', '$scope', '$http', '$localStorage', 'APPCONST', '$modal', '$log', '$anchorScroll', '$state',
        function ($window, $scope, $http, $localStorage, APPCONST, $modal, $log, $anchorScroll, $state) {
            $scope.app.settings.asideHide = false;
            $scope.app.subHeader.goBackHide = true;
            $scope.overlays = [];
            $scope.offlineOpts = {retryInterval: 5000};
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
            $scope.mapLabelCache = {};

            $scope.map_canvas = angular.element("#map_canvas");
            $scope.app.settings.mapScaleMax = false;

            $scope.toggleMapScale = function () {
                if (!$scope.isMapInited) {
                    return;
                }
                $scope.app.settings.mapScaleMax = !$scope.app.settings.mapScaleMax;
                // $scope.mapCenter = $scope.myMap.getCenter();
                if ($scope.app.settings.mapScaleMax) {
                    $scope.resizeMapScale();
                    angular.element(".map-scale-btn-wrapper").css('right', 30);
                } else {
                    $scope.map_canvas.parent().css('width', '');
                    $scope.map_canvas.css('width', '');
                    $scope.map_canvas.css('height', '');
                    angular.element(".map-scale-btn-wrapper").css('right', 25);
                }
                // $scope.myMap.setCenter($scope.mapCenter);
            };
            $scope.resizeMapScale = function () {
                $scope.map_canvas.parent().css('width', '100%');
                var width = $window.innerWidth;
                var height = $window.innerHeight - 115;
                if (width > 768) {
                    if (width <= 991) {
                        width = width - 330;
                        height = height + 30;
                    } else {
                        width = width - 370;
                    }
                } else {
                    height = height + 30;
                }
                $scope.map_canvas.css('width', width);
                $scope.map_canvas.css('height', height);
            };

            $scope.onMapClick = function ($event, $params) {
            };
            $scope.onMapLoaded = function ($event, $params) {
                $scope.addMarkers();
                $scope.isMapInited = true;
            };

            $scope.onMarkerClick = function (event) {
                $state.go('app.device.tab.info', {id: event.target.device.sensor_id, device: event.target.device});
            };

            /*$scope.onMarkerMouseOver = function (event) {
             // console.warn(event);
             // console.warn(event.target.device);
             var device = event.target.device;

             var infoWindow = $scope.mapLabelCache[device.sensor_id];
             if (null == infoWindow) {
             var opts = {
             width: 200,     // 信息窗口宽度
             height: 100,     // 信息窗口高度
             title: device.name, // 信息窗口标题
             enableMessage: true,//设置允许信息窗发送短息
             message: device.address
             };
             infoWindow = new BMap.InfoWindow("地址：" + device.address, opts);  // 创建信息窗口对象
             $scope.mapLabelCache[device.sensor_id] = infoWindow;
             }
             $scope.myMap.openInfoWindow(infoWindow, event.point); //开启信息窗口
             };*/

            /*$scope.onMarkerMouseout = function (event) {
             $scope.myMap.closeInfoWindow();
             // var sid = event.target.device.sensor_id;
             // if ($scope.mapLabelCache[sid]) {
             //     $scope.mapLabelCache[sid].hide();
             // }
             };*/

            if ($scope.app.cache.selectParentBranch) {
                $scope.app.subHeader.contentTitle = $scope.app.cache.selectParentBranch.label;
            }

            $scope.$on("BROADCAST_DEVICE_TREE_CLICK", function (event, data) {
                $scope.addMarkers();
            });

            $scope.init = function () {
                $scope.shouldAddAllSensorsToMap = true;
                // if(!$scope.myMap) {
                //     $scope.myMap = $scope.app.cache.dashboardMap;
                // }
                // $scope.addMarkers();
            };

            $scope.addMarkers = function () {
                if (!$scope.shouldAddAllSensorsToMap) {
                    return;
                }
                if (!$scope.virtualSensors) {
                    $scope.virtualSensors = $scope.app.cache.virtualSensors;
                }
                if (!$scope.virtualSensors || $scope.virtualSensors.length <= 0) {
                    return;
                }
                $scope.myMap.clearOverlays();
                if (null != $scope.virtualSensors) {
                    for (var i = 0; i < $scope.virtualSensors.length; i++) {
                        var item = $scope.virtualSensors[i].sensor;
                        var point = new BMap.Point(item.longitude, item.latitude);
                        var marker = new BMap.Marker(point);
                        var label = new BMap.Label(item.name, {position: point, offset: new BMap.Size(-30, -25)});
                        label.setStyle({borderColor: '#ccc'});
                        marker.setLabel(label);
                        marker.device = item;
                        $scope.myMap.addOverlay(marker);
                        $scope.myMap.centerAndZoom(point, 13);
                        marker.addEventListener('click', $scope.onMarkerClick);
                        // marker.addEventListener('mouseover', $scope.onMarkerMouseOver);
                        // marker.addEventListener('mouseout', $scope.onMarkerMouseout);
                    }
                }
                $scope.shouldAddAllSensorsToMap = true;
            };

            $http.get(APPCONST.CTX + APPCONST.SENSORS).then(function (response) {
                $scope.virtualSensors = response.data.data;
                if (!$scope.virtualSensors) {
                    return;
                }
                for (var i = 0; i < $scope.virtualSensors.length; i++) {
                    $scope.virtualSensors[i].sensor.csl = 12 + parseInt(Math.random() * 100) / 100.0;
                    $scope.virtualSensors[i].data.forEach(function (dateItem) {
                        if (dateItem.comp_type === 'flowmeter_sensor') {
                            dateItem.time = $scope.formatDate(new Date(dateItem.time), "yyyy年MM月dd日HH:mm:ss");
                            $scope.virtualSensors[i].sensor.flowmeter_data = dateItem;
                        }
                    });
                }
                $scope.app.cache.virtualSensors = $scope.virtualSensors;
                $scope.addMarkers();
                // console.warn($scope.virtualSensors);
            });

            $scope.showInMap = function (id) {
                var item = null;
                for (var i = 0; i < $scope.virtualSensors.length; i++) {
                    if (id === $scope.virtualSensors[i].sensor.sensor_id) {
                        item = $scope.virtualSensors[i].sensor;
                        break;
                    }
                }
                if (null != item) {
                    $scope.myMap.clearOverlays();
                    var point = new BMap.Point(item.longitude, item.latitude);
                    var marker = new BMap.Marker(point);
                    marker.device = item;
                    $scope.myMap.addOverlay(marker);
                    $scope.myMap.centerAndZoom(point, 13);
                    marker.addEventListener('click', $scope.onMarkerClick);
                }
                $scope.shouldAddAllSensorsToMap = true;
                $anchorScroll();
                $scope.Toast("success", "提示", "地图定位成功.");
            };

            $scope.$on("WS_MESSAGE", function (event, data) {
                // console.warn(data);
                var temp = {};
                try {
                    angular.forEach(data, function (device) {
                        if (device.comp_type === 'flowmeter_sensor') {
                            temp[device.sensor_id] = {
                                instant: device.instant,
                                positive_total: device.positive_total
                            };
                        }
                    });
                } catch (e) {
                    console.error(e);
                }
                if (!$scope.virtualSensors) {
                    return;
                }
                var timestamp = new Date();
                var timeFormatText = $scope.formatDate(timestamp, "yyyy年MM月dd日HH:mm:ss");
                for (var i = 0; i < $scope.virtualSensors.length; i++) {
                    var item = $scope.virtualSensors[i].sensor;
                    try {
                        $scope.virtualSensors[i].sensor.flowmeter_data.instant = temp[item.sensor_id].instant;
                        $scope.virtualSensors[i].sensor.flowmeter_data.positive_total = temp[item.sensor_id].positive_total;
                        $scope.virtualSensors[i].sensor.flowmeter_data.time = timeFormatText;
                    } catch (e) {
                    }
                }
            });

            $scope.$on("bc.event.win.resize", function (event, data) {
                if ($scope.app.settings.mapScaleMax) {
                    $scope.resizeMapScale();
                }
            });
        }])
;