'use strict';

// DeviceDetail controller
app.controller('DeviceTabWqCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        /*$scope.app.subHeader.goBackHide = false;
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
         };*/

        $scope.data = {
            "N_data": {"water_in": "3.2", "water_out": "11"},
            "P_data": {"water_in": "50", "water_out": "12.23"},
            "C_data": {"water_in": "18", "water_out": "7"},
            "PH_data": {"water_in": "6", "water_out": "10"}
        };

        $scope.chartNH3N = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},
                {name: '出水口', data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: ['06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00', '00:00', '02:00', '04:00']},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 250}
        };

        $scope.chartZL = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},
                {name: '出水口', data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: ['06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00', '00:00', '02:00', '04:00']},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 250}
        };

        $scope.chartCOD = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},
                {name: '出水口', data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: ['06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00', '00:00', '02:00', '04:00']},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 250}
        };

        $scope.chartPH = {
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'mg/l', style: {padding: 10, fontWeight: 'bold'}}},
            series: [
                {name: '进水口', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},
                {name: '出水口', data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}
            ],
            title: {text: '　', x: -50}, loading: false,
            xAxis: {categories: ['06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00', '00:00', '02:00', '04:00']},
            yAxis: {title: {text: '(mg/l)'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 250}
        };
    }])
;