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

        $scope.setupTreeItem = function (item) {
            item.label = item.name;
            if (item.nodeType == 1) {
                item.icon = 'fa fa-male c-red';
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

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_ROUTE).then(function (response) {
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
            $scope.tree.expand_all();
        });

        $scope.treeItemClick = function (branch) {
            if (branch.nodeType == 1) {
                $scope.$emit("EMIT_ROUTE_TREE_ITEM_CLICK", branch);
            } else {
                $scope.$emit("EMIT_ROUTE_TREE_CLICK", branch);
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

        $scope.$on("BROADCAST_ROUTE_TREE_CLICK", function (event, data) {
            // console.warn(data);
            $scope.myMap.clearOverlays();
        });
        $scope.$on("BROADCAST_ROUTE_TREE_ITEM_CLICK", function (event, data) {
            // console.error(data);
            $http.get(APPCONST.CTX + APPCONST.ROUTE_POINTS.replace("{id}", data.entity_id)).then(function (response) {
                console.warn(response);

                $scope.myMap.clearOverlays();
                if (response.data.data && response.data.data.length > 0) {
                    var points = [];
                    angular.forEach(response.data.data, function (item) {
                        points.push(new BMap.Point(item.longitude, item.latitude));
                    });
                    var polyline = new BMap.Polyline(points, {
                        strokeColor: "blue",
                        strokeWeight: 2,
                        strokeOpacity: 0.5
                    });
                    $scope.myMap.addOverlay(polyline);

                    var marker = new BMap.Marker(
                        points[0], {icon: new BMap.Icon("img/marker_blue.png", new BMap.Size(32, 32))}
                    );
                    $scope.myMap.addOverlay(marker);
                    if (response.data.data.length > 1) {
                        var markerStart = new BMap.Marker(
                            points[response.data.data.length - 1], {icon: new BMap.Icon("img/marker_red.png", new BMap.Size(32, 32))}
                        );
                        $scope.myMap.addOverlay(markerStart);
                    }
                    $scope.myMap.centerAndZoom(points[0], 13);
                }
            }, function (response) {
                toaster.pop('error', '提示', '加载巡检轨迹出错，服务器响应异常');
            });

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

    }])
;