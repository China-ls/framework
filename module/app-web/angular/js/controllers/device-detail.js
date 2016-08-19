'use strict';

/* Controllers */

// DeviceDetail controller
app.controller('DeviceDetailCtrl', ['$scope', '$stateParams', '$state', '$http', 'NETCONST',
    function ($scope, $stateParams, $state, $http, NETCONST) {
        $scope.app.subHeader.subAsideTitle = '设备详情';
        $scope.app.subHeader.goBackHide = false;

        $http.get(NETCONST.CTX + NETCONST.SENSOR_BY_ID + $stateParams.id).then(function (response) {
            $scope.device = response.data.data;
            // if ($scope.device.status === 'NORMAL') {
            $scope.device.dsplay_status = "正常";
            if (!$scope.device.instant) {
                $scope.device.instant = "0";
            }
            // }
            // console.warn($scope.device);
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

        $scope.formatDate = function (date, formatStr) {
            var str = formatStr;
            var Week = ['日', '一', '二', '三', '四', '五', '六'];

            str = str.replace(/yyyy|YYYY/, date.getFullYear());
            str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));

            str = str.replace(/MM/, date.getMonth() > 9 ? (date.getMonth() + 1).toString() : '0' + (date.getMonth() + 1));
            str = str.replace(/M/g, (date.getMonth() + 1));

            str = str.replace(/w|W/g, Week[date.getDay()]);

            str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
            str = str.replace(/d|D/g, date.getDate());

            str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
            str = str.replace(/h|H/g, date.getHours());
            str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
            str = str.replace(/m/g, date.getMinutes());

            str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
            str = str.replace(/s|S/g, date.getSeconds());
            return str;
        };

        $scope.$on("WS_MESSAGE", function (event, data) {
            // console.warn(data);
            try {
                angular.forEach(data, function (item) {
                    if (item.comp_id === '2') {
                        $scope.device.instant = item.instant;
                        $scope.device.positive_total = item.positive_total;
                        $scope.device.updateTime = $scope.formatDate(new Date(), "yyyy年MM月dd日HH:mm:ss");
                        // } else if (data.comp_id === '4') {
                        //     $scope.device.image = data.image;
                    }
                });
            } catch (e){}
        });

        $scope.takepick = function () {
            $scope.wsSend('{"channel_id" : "4","operation" : "take_photo","param" : 1,"sensor_id" : "59ec0ac4-2182-4960-9166-3ce62738ef99"}');
            $scope.Toast('success', '提示', '拍照成功!');
        };
    }]
);