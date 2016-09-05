'use strict';

// DeviceDetail controller
app.controller('DeviceTabWaterCtrl', ['$scope', '$http', 'APPCONST', function ($scope, $http, APPCONST) {
    $scope.chartHandlerWater = {
        options: {
            chart: {type: 'line', zoomType: 'x'},
            tooltip: {valueSuffix: 'L/H', style: {padding: 10, fontWeight: 'bold'}}
        },
        series: [],
        title: {text: ''}, loading: false,
        xAxis: {categories: []},
        yAxis: {title: {text: ' L/H'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
        useHighStocks: false, size: {height: 400}
    };
    $scope.getWaterDataPromise = $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_TODAY.replace("{id}", $scope.$stateParams.id))
        .then(function (response) {
            try {
                console.warn(response);
                var array = response.data.data;
                if (array) {
                    var series = {name: '瞬时流量', data: []};
                    array.forEach(function (item) {
                        if (!item.instant) {
                            item.instant = 0;
                        }
                        series.data.push(item.instant);
                        $scope.chartHandlerWater.xAxis.categories.push(
                            $scope.formatDate(new Date(item.time), "HH:mm:ss")
                        );
                    });
                    $scope.chartHandlerWater.series.push(series);
                }
            } catch (e) {
                $log.debug(e);
            }
        });
}])
;