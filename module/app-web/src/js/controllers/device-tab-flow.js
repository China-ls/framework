'use strict';

// DeviceDetail controller
app.controller('DeviceTabWaterCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        if (!$scope.$stateParams) {
            $state.go('app.device');
            return;
        }

        // 最新 数据
        $scope.loadLatestDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_WATER.replace("{id}", $scope.$stateParams.id))
            .then(function (response) {
                $scope.waterData = response.data.data;
                try {
                    $scope.waterData.update_time = $scope.formatDate(new Date($scope.waterData.latest.time), "yyyy年MM月dd日 HH时mm分ss秒")
                } catch (e) {
                }
                // console.warn($scope.waterData);
            });

        $scope.chartHandlerWater = {
            options: {
                chart: {type: 'line', zoomType: 'x'},
                tooltip: {valueSuffix: '升/小时', style: {padding: 10, fontWeight: 'bold'}}
            },
            series: [],
            title: {text: ''}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: ' 升/小时'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };
        $scope.getWaterDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_TODAY.replace("{id}", $scope.$stateParams.id))
            .then(function (response) {
                try {
                    // console.warn(response);
                    var array = response.data.data;
                    var series = {name: '瞬时流量', data: []};
                    if (array && array.length > 0) {
                        array.forEach(function (item) {
                            if (!item.instant) {
                                item.instant = 0;
                            }
                            series.data.push(item.instant);
                            $scope.chartHandlerWater.xAxis.categories.push(
                                $scope.formatDate(new Date(item.time), "HH:mm:ss")
                            );
                        });
                    } else {
                        series.data.push(0);
                        $scope.chartHandlerWater.xAxis.categories.push(
                            $scope.formatDate(new Date(), "HH:mm:ss")
                        );
                    }
                    $scope.chartHandlerWater.series.push(series);
                } catch (e) {
                    $log.debug(e);
                }
            });

        $scope.chartHandlerDayTotal = {
            options: {chart: {type: 'column'}, tooltip: {valueSuffix: '立方米', style: {padding: 10, fontWeight: 'bold'}}},
            series: [],
            title: {text: ''}, loading: false,
            xAxis: {tickInterval: 1, labels: {step: 4}, categories: []},
            yAxis: {title: {text: '立方米'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            plotOptions: {bar: {dataLabels: {enabled: true}, enableMouseTracking: false}},
            useHighStocks: false, size: {height: 200}
        };

        $scope.loadDayTotalData = function () {
            $scope.loadDayPositivePromise = $http.get(APPCONST.CTX
                + APPCONST.SENSOR_DATA_DEGREE
                    .replace("{id}", $scope.$stateParams.id)
                    .replace("{type}", $scope.chartTypePositive)
                    .replace("{field}", 'positive_total')
            )
                .then(function (response) {
                    try {
                        var data = response.data.data;
                        // console.warn(data);
                        if (data) {
                            var series = {
                                name: $scope.chartSeriesName,
                                data: data.values/*, dataLabels: {enabled: true}*/,
                                color: APPCONST.CHARTS_COLORS[Math.ceil(Math.random() * APPCONST.CHARTS_COLORS.length) % 15]
                            };
                            $scope.chartHandlerDayTotal.xAxis.categories = data.keys;
                        }
                        $scope.chartHandlerDayTotal.series.splice(0);
                        $scope.chartHandlerDayTotal.series.push(series);
                    } catch (e) {
                        $log.debug(e);
                    }
                });
        };

        $scope.setDayTotalChartType = function (type) {
            $scope.chartHandlerDayTotal.options.chart.type = type;
        };

        $scope.setDayTotalDataType = function (type) {
            $scope.chartTypePositive = type;
            if (type === 0) {
                $scope.chartHandlerDayTotal.xAxis.labels.step = 4;
                $scope.chartSeriesName = '时处理量';
            } else if (type === 1) {
                $scope.chartHandlerDayTotal.xAxis.labels.step = 4;
                $scope.chartSeriesName = '日处理量';
            } else if (type === 2) {
                $scope.chartHandlerDayTotal.xAxis.labels.step = 2;
                $scope.chartSeriesName = '月处理量';
            }
            $scope.loadDayTotalData();
        };

        $scope.setDayTotalDataType(0);

        $scope.ccc = APPCONST.CHARTS_COLORS;

    }])
;