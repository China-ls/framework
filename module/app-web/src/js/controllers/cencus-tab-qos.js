'use strict';

// DeviceDetail controller
app.controller('CencusTabQosTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = true;
        $scope.app.subHeader.contentTitle = 'Qos分析';
    }]
);

app.controller('CencusTabQosCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.filterQuery = function () {
            $scope.loadDataPromise = $http.post(APPCONST.CTX + APPCONST.CENCUS_QOS, $scope.filter)
                .then(function (response) {
                    // console.warn(response);
                    $scope.filterResults = response.data.data;
                    if ($scope.filterResults) {
                        angular.forEach($scope.filterResults, function (item) {
                            angular.forEach(item.data, function (itemData) {
                                if (itemData.comp_type = 'flowmeter_sensor') {
                                    try {
                                        item.flow_time = $scope.formatDate(new Date(itemData.time), 'yyyy年MM月dd日HH十mm分ss秒');
                                    } catch (e){}
                                } else if (itemData.comp_type = 'electricmeter_sensor') {
                                    item.temperature = itemData.temperature;
                                    try {
                                        item.el_time = $scope.formatDate(new Date(itemData.time), 'yyyy年MM月dd日HH十mm分ss秒');
                                    } catch (e){}
                                } else if (itemData.comp_type = 'cardreader_sensor') {
                                    try {
                                        item.routing_time = $scope.formatDate(new Date(itemData.time), 'yyyy年MM月dd日HH十mm分ss秒');
                                    } catch (e){}
                                } else if (itemData.comp_type = 'camera_controller') {
                                    try {
                                        item.image_time = $scope.formatDate(new Date(itemData.time), 'yyyy年MM月dd日HH十mm分ss秒');
                                    } catch (e){}
                                }
                            });
                            // var dataArray = item.data;
                        });
                    }
                });
        };
    }])
;