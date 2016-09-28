'use strict';

// DeviceDetail controller
app.controller('DeviceTabElCtrl', ['$scope', '$http', 'APPCONST', function ($scope, $http, APPCONST) {
    $scope.app.subHeader.goBackHide = false;
    $scope.app.subHeader.goBackSref = 'app.device';

    $scope.chartElectric = {
        options: {chart: {type: 'column'}, tooltip: {valueSuffix: '度', style: {padding: 10, fontWeight: 'bold'}}},
        series: [{name: '用电量', data: [10, 15, 12, 8, 7]}],
        title: {text: ''}, loading: false,
        xAxis: {tickInterval: 1, labels: {step: 4}, categories: []},
        yAxis: {title: {text: '度'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
        useHighStocks: false, size: {height: 200}
    };

    // 最新 数据
    $scope.loadLatestDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_ELECTRIC.replace("{id}", $scope.$stateParams.id))
        .then(function (response) {
            $scope.elData = response.data.data;
            // console.warn($scope.elData);
            try {
                $scope.elData.update_time = $scope.formatDate(new Date($scope.elData.latest.time), "yyyy年MM月dd日 HH时mm分ss秒")
            } catch (e){}
            // console.warn($scope.waterData);
        });

    $scope.loadData = function () {
        $scope.loadDayPositivePromise = $http.get(APPCONST.CTX
            + APPCONST.SENSOR_DATA_DEGREE
                .replace("{id}", $scope.$stateParams.id)
                .replace("{type}", $scope.chartElectricType)
                .replace("{field}", 'power')
        )
            .then(function (response) {
                try {
                    var data = response.data.data;
                    // console.warn(data);
                    if (data) {
                        var series = {name: $scope.chartSeriesName, data: data.values/*, dataLabels: {enabled: true}*/};
                        $scope.chartElectric.xAxis.categories = data.keys;
                    }
                    $scope.chartElectric.series.splice(0);
                    $scope.chartElectric.series.push(series);
                } catch (e) {
                    $log.debug(e);
                }
            });
    };

    $scope.setDataChartType = function (type) {
        $scope.chartElectric.options.chart.type = type;
    };

    $scope.setDataType = function (type) {
        $scope.chartElectricType = type;
        if (type === 0) {
            $scope.chartElectric.xAxis.labels.step = 4;
            $scope.chartSeriesName = '时用电量';
        } else if (type === 1) {
            $scope.chartElectric.xAxis.labels.step = 4;
            $scope.chartSeriesName = '日用电量';
        } else if (type === 2) {
            $scope.chartElectric.xAxis.labels.step = 2;
            $scope.chartSeriesName = '月用电量';
        }
        $scope.loadData();
    };

    $scope.setDataType(0);

}])
;