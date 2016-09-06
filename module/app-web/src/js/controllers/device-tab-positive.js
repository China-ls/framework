'use strict';

// DeviceDetail controller
app.controller('DeviceTabPositiveCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_SELECT_DEVICE];
        if (!$scope.stateParams) {
            $state.go('app.device');
            return;
        }
        $scope.chartHandlerDayTotal = {
            options: {chart: {type: 'column'}, tooltip: {valueSuffix: '立方米', style: {padding: 10, fontWeight: 'bold'}}},
            series: [],
            title: {text: ''}, loading: false,
            xAxis: {categories: []},

            yAxis: {title: {text: '立方米'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            plotOptions: {bar: {dataLabels: {enabled: true}, enableMouseTracking: false}},
            useHighStocks: false, size: {height: 400}
        };
        $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_TOTAL_MONTH.replace("{id}", $scope.stateParams.id))
            .then(function (response) {
                try {
                    var data = response.data.data;
                    // console.warn(data);
                    if (data) {
                        var series = {name: '日处理量', data: data.values, dataLabels: {enabled: true}};
                        $scope.chartHandlerDayTotal.xAxis.categories = data.keys;
                    }
                    $scope.chartHandlerDayTotal.series.push(series);
                } catch (e) {
                    $log.debug(e);
                }
            });
    }])
;