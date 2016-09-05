'use strict';

// DeviceDetail controller
app.controller('DeviceTabElCtrl', ['$scope', '$http', 'APPCONST', function ($scope, $http, APPCONST) {
    $scope.chartElectric = {
        options: {chart: {type: 'line'}, tooltip: {valueSuffix: '度', style: {padding: 10, fontWeight: 'bold'}}},
        series: [{name: '用电量', data: [10, 15, 12, 8, 7]}],
        title: {text: ''}, loading: false,
        xAxis: {categories: ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07']},
        yAxis: {title: {text: '度'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
        useHighStocks: false, size: {height: 400}
    };
}])
;