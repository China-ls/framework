'use strict';

// DeviceDetail controller
app.controller('CencusDeviceTypeTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = true;
        $scope.app.subHeader.contentTitle = '分类统计';
    }]);

app.controller('CencusDeviceTypeCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {

        $scope.chartHandlerDeviceType = {
            options: {chart: {type: 'pie'}, tooltip: {valueSuffix: '', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '设备个数', data: []}],
            title: {text: ''}, loading: false,
            xAxis: {tickInterval: 1, labels: {step: 4}, categories: []},
            yAxis: {title: {text: ''}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };

        $scope.chartHandlerDeviceStatus = {
            options: {chart: {type: 'pie'}, tooltip: {valueSuffix: '', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '设备个数', data: []}],
            title: {text: ''}, loading: false,
            xAxis: {tickInterval: 1, labels: {step: 4}, categories: []},
            yAxis: {title: {text: ''}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };

        // 最新 数据
        $scope.promiseLoadDeviceType = $http.get(APPCONST.CTX + APPCONST.CENCUS_DEVICE_TYPE)
            .then(function (response) {
                try {
                    $scope.cencusDeviceType = response.data.data;
                    // console.warn(data);
                    if ($scope.cencusDeviceType) {
                        var array = [];
                        angular.forEach($scope.cencusDeviceType, function (item, index) {
                            item.color = APPCONST.CHARTS_COLORS_DEVICE_TYPE[index % 8];
                            array.push({name: item.name, y: item.count, color: item.color});
                        });
                        var series = {name: '设备个数', data: array};
                    }
                    $scope.chartHandlerDeviceType.series.splice(0);
                    $scope.chartHandlerDeviceType.series.push(series);
                } catch (e) {
                }
            });

        $scope.promiseLoadDeviceStatus = $http.get(APPCONST.CTX + APPCONST.CENCUS_DEVICE_ONLINE)
            .then(function (response) {
                try {
                    $scope.cencusDeviceStatus = response.data.data;
                    // console.warn(data);
                    if ($scope.cencusDeviceStatus) {
                        var array = [];
                        angular.forEach($scope.cencusDeviceStatus, function (item) {
                            if (item.online == 0) {
                                item.name = '未知';
                            } else if (item.online == 1) {
                                item.name = '在线';
                                item.color = '#68D533';
                            } else if (item.online == 2) {
                                item.name = '离线';
                                item.color = '#808080';
                            } else if (item.online == 3) {
                            }
                            array.push({name: item.name, y: item.count, color: item.color});
                        });
                        var series = {name: '设备个数', data: array};
                    }
                    $scope.chartHandlerDeviceStatus.series.splice(0);
                    $scope.chartHandlerDeviceStatus.series.push(series);
                } catch (e) {
                }
            });

        $scope.filterDeviceType = function () {
            $http.post(APPCONST.CTX + APPCONST.CENCUS_DEVICE_FILTER, $scope.filter)
                .then(function (response) {
                    // console.warn(response);
                    $scope.filterResults = response.data.data;
                });
        };
    }])
;