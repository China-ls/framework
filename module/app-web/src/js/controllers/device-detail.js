'use strict';

/* Controllers */

// DeviceDetail controller
app.controller('DeviceDetailCtrl', ['$scope', '$stateParams', '$state', '$http', 'NETCONST',
    function ($scope, $stateParams, $state, $http, NETCONST) {
        $scope.app.subHeader.subAsideTitle = '设备详情';
        $scope.app.subHeader.goBackHide = false;

        $http.get(NETCONST.CTX + NETCONST.SENSOR_BY_ID + $stateParams.id).then(function (response) {
            $scope.device = response.data.data;
            if ($scope.device.status === 'NORMAL') {
                $scope.device.dsplay_status = "正常";
            }
            console.warn($scope.device);
            $scope.app.subHeader.contentTitle = $scope.device.name + '设备详情';
        }, function (response) {

        });

        $scope.chartElectric = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '度', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '用电量', data: [10, 15, 12, 8, 7]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '度'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };
        $scope.chartHandlerWater = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '吨', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '处理量', data: [12, 12.1, 13, 13.1, 7.9, 11, 13]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '吨'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };

        $scope.chartWaterQ = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '吨', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '处理量', data: [12, 12.1, 13, 13.1, 7.9, 11, 13]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '吨'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };

        $scope.walk = [
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '},
            {time: ' ', name: ' ', tel: ' ', content: ' '}
        ];

    }]
);