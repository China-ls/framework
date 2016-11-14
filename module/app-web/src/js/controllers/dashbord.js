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

        $scope.asideQueryBranches = [];
        $scope.asideQueryBranchesIndex = -1;

        $scope.clearAsideQuery = function () {
            $scope.asideQueryBranches.splice(0);
            $scope.asideQueryBranchesIndex = -1;
            $scope.asideQuery = '';
        };
        $scope.onQueryTextChange = function () {
            $scope.asideQueryBranches.splice(0);
            $scope.asideQueryBranchesIndex = -1;

            if ($scope.asideQuery.length <= 0) {
                return;
            }
            angular.forEach($scope.treeData, function (item) {
                $scope.filterByAsideQuery(item);
            });
            $scope.goPreFilterResult();
        };
        $scope.onAsideQuery = function () {
            $scope.asideQueryBranches.splice(0);
            $scope.asideQueryBranchesIndex = -1;
            angular.forEach($scope.treeData, function (item) {
                $scope.filterByAsideQuery(item);
            });
            $scope.goPreFilterResult();
            // console.warn($scope.asideQueryBranches);
        };
        $scope.filterByAsideQuery = function (rootBranch) {
            if (rootBranch.label && rootBranch.label.indexOf($scope.asideQuery) != -1) {
                $scope.asideQueryBranches.push(rootBranch);
            }
            if (!rootBranch.children) {
                return;
            }
            for (var i = 0; i < rootBranch.children.length; i++) {
                $scope.filterByAsideQuery(rootBranch.children[i]);
            }
        };
        $scope.goPreFilterResult = function () {
            if ($scope.asideQueryBranches.length <= 0) {
                return;
            }
            $scope.asideQueryBranchesIndex--;
            if ($scope.asideQueryBranchesIndex < 0) {
                $scope.asideQueryBranchesIndex = 0;
            }
            $scope.tree.select_branch($scope.asideQueryBranches[$scope.asideQueryBranchesIndex]);
        };
        $scope.goNextFilterResult = function () {
            var len = $scope.asideQueryBranches.length;
            if (len <= 0) {
                return;
            }
            $scope.asideQueryBranchesIndex++;
            if ($scope.asideQueryBranchesIndex >= len) {
                $scope.asideQueryBranchesIndex = 0;
            }
            $scope.tree.select_branch($scope.asideQueryBranches[$scope.asideQueryBranchesIndex]);
        };

        $scope.setupTreeItem = function (item) {
            item.label = item.name;
            if (item.nodeType == 1) {
                item.icon = 'fa fa-map-marker c-green';
            } else {
                var color = 'c-red';
                if (item.level === 1) {
                    color = 'c-yellow';
                } else if (item.level === 2) {
                    color = 'c-green';
                } else if (item.level >= 3) {
                    color = 'c-blue';
                }
                item.icon = 'fa fa-flag ' + color;
            }
        };
        $scope.addToChildren = function (treeItem, item) {
            if (treeItem.id == item.parentId) {
                if (!treeItem.children) {
                    treeItem.children = [];
                }
                $scope.setupTreeItem(item);
                treeItem.children.push(item);
            } else if (treeItem.children) {
                angular.forEach(treeItem.children, function (node) {
                    $scope.addToChildren(node, item);
                });
            }
        };

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT).then(function (response) {
            if (!response.data.data) {
                return;
            }
            var data = response.data.data.data;
            angular.forEach(data, function (item) {
                if ($scope.treeData.length <= 0) {
                    $scope.setupTreeItem(item);
                    $scope.treeData.push(item);
                } else {
                    angular.forEach($scope.treeData, function (treeItem) {
                        $scope.addToChildren(treeItem, item);
                    });
                }
            });
            try {
                $scope.tree.expand_all();
            } catch (e) {
            }
            try {
                $scope.tree.select_first_branch();
            } catch (e) {
            }
        });

        $scope.treeItemClick = function (branch) {
            // console.warn(branch);
            $scope.app.subHeader.contentTitle = branch.name;
            // console.warn(branch.name);
            if (branch.nodeType == 1) {
                $scope.app.cache.selectParentBranch = $scope.tree.get_parent_branch(branch);
                // console.warn($scope.tree.get_parent_branch(branch));
                $localStorage.selectDeviceId = branch.entity_id;
                $localStorage.selectDeviceName = branch.name;
                $state.go("app.device.tab.info", {id: branch.entity_id, device: branch});
            } else {
                $localStorage.selectDeviceId = null;
                $scope.app.cache.selectParentBranch = null;
                $state.go("app.device", {id: branch.id}, {inherit: false}).then(function (response) {
                    $scope.$emit("EMIT_DEVICE_TREE_CLICK", branch);
                });
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
                $localStorage.selectDeviceId = event.target.device.sensor_id;
                $localStorage.selectDeviceName = event.target.device.name;
                $state.go('app.device.tab.info', {id: event.target.device.sensor_id, device: event.target.device});
            };

            $scope.onShowDeviceDetail = function (item) {
                $localStorage.selectDeviceId = item.sensor_id;
                $localStorage.selectDeviceName = item.name;
                $state.go('app.device.tab.info', {id: item.sensor_id, device: item});
            };

            if ($scope.app.cache.selectParentBranch) {
                $scope.app.subHeader.contentTitle = $scope.app.cache.selectParentBranch.label;
            }

            $scope.$on("BROADCAST_DEVICE_TREE_CLICK", function (event, data) {
                $scope.app.subHeader.contentTitle = data.name;
                $scope.loadDevices(data.id);
            });

            $scope.init = function () {
                $scope.shouldAddAllSensorsToMap = true;
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

            $scope.loadDevices = function (dpt) {
                $scope.loadDevicesPromise = $http.get(APPCONST.CTX + APPCONST.SENSORS_BY_DEPARTMENT + dpt)
                    .then(function (response) {
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
            };

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