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

app.controller('AsideDeviceCtrl', ['$scope', '$http', '$localStorage', '$state', 'NETCONST',
    function ($scope, $http, $localStorage, $state, NETCONST) {
        $scope.treeData = [];
        $scope.tree = {};

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
                    child.icon = 'fa  fa-map-marker c-green';
                    children.push(child);
                });
            }
            data.children = children;
            data.label = data.name;
            return data;
        };

        $http.get(NETCONST.CTX + NETCONST.DEPARTMENT).then(function (response) {
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
                $scope.app.cache.selectParentBranch = null;
            } else if (branch.type === 'device') {
                $scope.app.cache.selectParentBranch = $scope.tree.get_parent_branch(branch);
                // console.warn($scope.tree.get_parent_branch(branch));
                $state.go("app.device.detail", {id: branch.uuid});
            }
        };
    }]
);


app.controller('DashbordCtrl', ['$scope', '$http', '$localStorage', 'NETCONST',
    function ($scope, $http, $localStorage, NETCONST) {
        $scope.overlays = [];
        $scope.offlineOpts = {retryInterval: 5000};
        $scope.mapOptions = {
            center: {
                longitude: 120.95281,
                latitude: 30.883874
            },
            zoom: 17,
            city: 'ShangHai',
            enableMapClick: false,
            clientHeight: $scope.baidumapeight,
            overlays: $scope.overlays
        };

        $scope.onMapClick = function ($event, $params) {
            console.warn('clickmap');
        };
        $scope.onMapLoaded = function ($event, $params) {
            // $scope.app.cache.dashboardMap = $scope.myMap;
            $scope.addMarkers();
        };

        if ($scope.app.cache.selectParentBranch) {
            $scope.app.subHeader.goBackHide = true;
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
            if(!$scope.shouldAddAllSensorsToMap) {
                return;
            }
            if(!$scope.virtualSensors) {
                $scope.virtualSensors = $scope.app.cache.virtualSensors;
            }
            if (!$scope.virtualSensors || $scope.virtualSensors.length <= 0) {
                return;
            }
            $scope.myMap.clearOverlays();
            for (var i = 0; i < $scope.virtualSensors.length; i++) {
                var item = $scope.virtualSensors[i];
                var point = new BMap.Point(item.longitude, item.latitude);
                var marker = new BMap.Marker(point);
                $scope.myMap.addOverlay(marker);
                $scope.myMap.panTo(point);
            }
            $scope.shouldAddAllSensorsToMap = true;
        };

        $http.get(NETCONST.CTX + NETCONST.SENSORS).then(function (response) {
            $scope.virtualSensors = response.data.data;
            for( var i = 0; i < $scope.virtualSensors.length; i ++) {
                $scope.virtualSensors[i].csl = 12 + parseInt(Math.random() * 100) / 100.0;
            }
            $scope.app.cache.virtualSensors = $scope.virtualSensors;
            $scope.addMarkers();
            // console.warn($scope.virtualSensors);
        });

        $scope.showInMap = function (id) {
            var item = null;
            for (var i = 0; i < $scope.virtualSensors.length; i++) {
                if( id === $scope.virtualSensors[i].id ) {
                    item = $scope.virtualSensors[i];
                    break;
                }
            }
            if (null != item) {
                $scope.myMap.clearOverlays();
                var point = new BMap.Point(item.longitude, item.latitude);
                var marker = new BMap.Marker(point);
                $scope.myMap.addOverlay(marker);
                $scope.myMap.panTo(point);
                // $scope.myMap.addMaprker();
            }
            $scope.shouldAddAllSensorsToMap = true;
            $scope.Toast("success", "提示", "地图定位成功.");
        };

        $scope.$on("WS_MESSAGE", function (event, data) {
            // console.warn(data);
            var temp = {};
            try {
                angular.forEach(data, function (device) {
                    if (device.comp_id === '2') {
                        temp[device.uuid] = {
                            instant: device.instant,
                            positive_total: device.positive_total
                        };
                    }
                });
            } catch (e) {
            }
            var timestamp = new Date();
            var timeFormatText = $scope.formatDate(timestamp, "yyyy年MM月dd日HH:mm:ss");
            for (var i = 0; i < $scope.virtualSensors.length; i++) {
                var item = $scope.virtualSensors[i];
                try {
                    $scope.virtualSensors[i].instant = temp[item.id].instant;
                    $scope.virtualSensors[i].positive_total = temp[item.id].positive_total;
                } catch (e) {
                }
                $scope.virtualSensors[i].updateTime = timeFormatText;
            }
        });
    }])
;