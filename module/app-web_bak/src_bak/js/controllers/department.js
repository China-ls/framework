'use strict';

/* Controllers */
app.controller('DepartmentDetailCtrl', ['$scope', '$http', 'GLOBAL', '$stateParams', '$state', '$localStorage',
    function ($scope, $http, GLOBAL, $stateParams, $state, $localStorage) {
        $scope.data = $stateParams.branch;
        if ($scope.data) {
            $localStorage.sd = $scope.data;
        } else {
            $scope.data = $localStorage.sd;
        }
        if (!$scope.data) {
            $state.go('app.dashboard');
        }

        console.warn($stateParams.branch);

        $scope.chartElectric = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '度', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '用电量', data: [10, 15, 12, 8, 7]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '度'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };
        $scope.chartHandlerWater = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '吨', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '处理量', data: [12, 12.1, 13, 13.1, 7.9, 11, 13]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '吨'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };

        $scope.chartWaterQ = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '吨', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '处理量', data: [12, 12.1, 13, 13.1, 7.9, 11, 13]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
            yAxis: {title: {text: '吨'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
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

    }])
;

app.controller('DeviceDetailCtrl', ['$scope', '$http', 'GLOBAL', '$stateParams', '$localStorage',
    function ($scope, $http, GLOBAL, $stateParams, $localStorage) {
        $scope.data = $stateParams.branch;
        if ($scope.data) {
            $localStorage.sd = $scope.data;
        } else {
            $scope.data = $localStorage.sd;
        }
        if (!$scope.data) {
            $state.go('app.dashboard');
        }

        $scope.device = {};

        $http.get(GLOBAL.API.sensor + "/" + $scope.data.uuid).then(function (response) {
            // console.warn(response);
            $scope.device = response.data.data;
        }, function (response) {
        });

    }])
;