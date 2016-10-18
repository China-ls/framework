'use strict';

/* Controllers */

// DashbordCtrl controller
app.controller('RouteHeaderCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '巡检员列表';
        $scope.app.subHeader.contentTitle = '巡检数据';
        $scope.app.subHeader.goBackHide = true;
    }]
);

app.controller('AsideRouteCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
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
                    child.icon = 'fa fa-male c-red';
                    children.push(child);
                });
            }
            data.children = children;
            data.label = data.name;
            return data;
        };

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_ROUTE).then(function (response) {
            var data = $scope.analysDepartment(response.data.data, 0);
            $scope.treeData.push(data);
            try {
                $scope.tree.expand_all();
                $scope.tree.select_first_branch();
            } catch (e) {
            }
            // console.warn('get asid route');
        }, function (response) {

        });

        $scope.treeItemClick = function (branch) {
            // if (branch.type === 'dpt') {
            //     $state.go("app.device", {id: branch.uuid}, {inherit: false});
            //     $scope.$emit("EMIT_DEVICE_TREE_CLICK", branch);
            //     $scope.app.cache.selectParentBranch = null;
            // } else if (branch.type === 'device') {
            //     $scope.app.cache.selectParentBranch = $scope.tree.get_parent_branch(branch);
            //     // console.warn($scope.tree.get_parent_branch(branch));
            //     $state.go("app.device.detail", {id: branch.uuid});
            // }
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

app.controller('RouteCtrl', ['$scope', '$http', '$localStorage', 'APPCONST', '$modal', '$log', '$anchorScroll',
    function ($scope, $http, $localStorage, APPCONST, $modal, $log, $anchorScroll) {
        $scope.app.settings.asideHide = false;
        $scope.app.subHeader.goBackHide = true;
        $scope.routeStarRadio = 'd';
        $scope.routeStarScore = 5;
        $scope.routeList = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
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

        $scope.onMapClick = function ($event, $params) {
        };
        $scope.onMapLoaded = function ($event, $params) {
            $scope.addMarkers();
        };

        $scope.onMarkerClick = function (event) {
            var modalInstance = $modal.open({
                templateUrl: 'mapModalContent.html',
                controller: 'MapModalInstanceCtrl',
                resolve: {
                    device: function () {
                        return event.target.device;
                    }
                }
            });
            modalInstance.result.then(function (selectedItem) {
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

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
            for (var i = 0; i < $scope.virtualSensors.length; i++) {
                var item = $scope.virtualSensors[i].sensor;
                var point = new BMap.Point(item.longitude, item.latitude);
                var marker = new BMap.Marker(point);
                marker.device = item;
                $scope.myMap.addOverlay(marker);
                $scope.myMap.centerAndZoom(point, 13);
                marker.addEventListener('click', $scope.onMarkerClick);
            }
            $scope.shouldAddAllSensorsToMap = true;
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

        });
    }])
;