'use strict';

// DeviceTabMapCtrl controller
app.controller('DeviceTabMapCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', '$window', '$modal',
    function ($scope, $http, $localStorage, $state, APPCONST, $window, $modal) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        $scope.sensor_id = $localStorage.selectDeviceId;
        if (!$scope.sensor_id) {
            $state.go('app.device');
            return;
        }
        $scope.app.subHeader.contentTitle = $localStorage.selectDeviceName;

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
        $scope.baidumap = angular.element(document.querySelector('#map_canvas'));
        $scope.onMapLoaded = function () {
            $scope.baidumap.height($window.innerHeight - 200);
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
            });
        };

        $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_BY_ID + $scope.sensor_id)
            .then(function (response) {
                if (!response.data.data || !response.data.data.sensor) {
                    return;
                }
                $scope.data = response.data.data;

                $scope.data.sensor.csl = 12 + parseInt(Math.random() * 100) / 100.0;
                if ($scope.data.data) {
                    $scope.data.data.forEach(function (dateItem) {
                        if (dateItem.comp_type === 'flowmeter_sensor') {
                            dateItem.time = $scope.formatDate(new Date(dateItem.time), "yyyy年MM月dd日HH:mm:ss");
                            $scope.data.sensor.flowmeter_data = dateItem;
                        }
                    });
                }

                var point = new BMap.Point($scope.data.sensor.longitude, $scope.data.sensor.latitude);
                var marker = new BMap.Marker(point);
                marker.device = $scope.data.sensor;
                $scope.myMap.addOverlay(marker);
                $scope.myMap.setCenter(point);
                marker.addEventListener('click', $scope.onMarkerClick);
            });
    }])
;