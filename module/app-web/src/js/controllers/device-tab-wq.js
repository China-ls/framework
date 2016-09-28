'use strict';

// DeviceDetail controller
app.controller('DeviceTabWqCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.device';

        if (!$scope.$stateParams) {
            $state.go('app.device');
            return;
        }
        $scope.chartWaterQ = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: '吨', style: {padding: 10, fontWeight: 'bold'}}},
            series: [{name: '处理量', data: [12, 12.1, 13, 13.1, 7.9, 11, 13]}],
            title: {text: ''}, loading: false,
            xAxis: {categories: ['09-23', '09-24', '09-25', '09-26', '09-27', '09-28', '09-29']},
            yAxis: {title: {text: '吨'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 400}
        };
    }])
;