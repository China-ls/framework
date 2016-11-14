'use strict';

// DeviceDetail controller
app.controller('DeviceTabWqCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.sensor_id = $localStorage.selectDeviceId;
        if (!$scope.sensor_id) {
            $state.go('app.device');
            return;
        }
        $scope.app.subHeader.contentTitle = $localStorage.selectDeviceName;

        $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_WATER_QUALITY_LIST.replace("{id}", $scope.sensor_id))
            .then(function (response) {
                $scope.clearChartData();
                $scope.data = response.data.data.data;
                if (!$scope.data) {
                    return;
                }
                var len = $scope.data.length;
                if (len > 0) {
                    for (var i = len - 1; i >= 0; i--) {
                        $scope.addPointToChart($scope.data[i]);
                    }
                    $scope.latest = $scope.data[0];
                }
            }, function (fail) {
                $scope.isSubmiting = false;
                $scope.Toast('warning', '提示', '读取水质数据失败，服务器响应异常。');
            });

        $scope.clearChartData = function () {
            $scope.chartNH3N.series[0].data.splice(0);
            $scope.chartNH3N.series[1].data.splice(0);

            $scope.chartZL.series[0].data.splice(0);
            $scope.chartZL.series[1].data.splice(0);

            $scope.chartCOD.series[0].data.splice(0);
            $scope.chartCOD.series[1].data.splice(0);

            $scope.chartPH.series[0].data.splice(0);
            $scope.chartPH.series[1].data.splice(0);
        };

        $scope.addPointToChart = function (item) {
            item.time = $scope.formatDate(new Date(item.time), 'yyyy-MM-dd HH:mm:ss');

            $scope.chartNH3N.series[0].data.push(item.nh3n_in);
            $scope.chartNH3N.series[1].data.push(item.nh3n_out);
            $scope.chartNH3N.xAxis.categories.push(item.time);

            $scope.chartZL.series[0].data.push(item.total_phosphorus_in);
            $scope.chartZL.series[1].data.push(item.total_phosphorus_out);
            $scope.chartZL.xAxis.categories.push(item.time);

            $scope.chartCOD.series[0].data.push(item.cod_in);
            $scope.chartCOD.series[1].data.push(item.cod_out);
            $scope.chartCOD.xAxis.categories.push(item.time);

            $scope.chartPH.series[0].data.push(item.ph_in);
            $scope.chartPH.series[1].data.push(item.ph_out);
            $scope.chartPH.xAxis.categories.push(item.time);
        };

        $scope.chartNH3N = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: []},
                {name: '出水口', data: []}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };

        $scope.chartZL = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: []},
                {name: '出水口', data: []}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };

        $scope.chartCOD = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: []},
                {name: '出水口', data: []}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };

        $scope.chartPH = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: []},
                {name: '出水口', data: []}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 300}
        };

        $scope.wq = {};
        $scope.isInputMode = false;
        $scope.isSubmiting = false;
        $scope.toggleInput = function () {
            $scope.isInputMode = !$scope.isInputMode;
        };

        $scope.submit = function () {
            if ($scope.isSubmiting) {
                return;
            }
            $scope.isSubmiting = true;
            // console.warn($scope.wq);
            $http.post(APPCONST.CTX + APPCONST.SENSOR_DATA_WATER_QUALITY_INSERT, angular.extend({}, $scope.wq, {sensor_id: $scope.sensor_id}))
                .then(function (response) {
                    $scope.addPointToChart(response.data.data);
                    $scope.latest = response.data.data;
                    $scope.isSubmiting = false;
                    $scope.wq = {};
                    $scope.Toast('success', '提示', '录入成功。');
                }, function (fail) {
                    $scope.isSubmiting = false;
                    $scope.Toast('warning', '提示', '录入失败，服务器响应异常。');
                });
        };

    }])
;