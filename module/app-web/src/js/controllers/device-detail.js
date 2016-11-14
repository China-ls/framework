'use strict';

/* Controllers */
/*app.controller('CarouselDemoCtrl', ['$scope', function ($scope) {
    $scope.myInterval = 5000;
    var slides = $scope.slides = [];
    $scope.addSlide = function () {
        slides.push({
            image: 'img/c' + slides.length + '.jpg',
            text: ['Carousel text #0', 'Carousel text #1', 'Carousel text #2', 'Carousel text #3'][slides.length % 4]
        });
    };
    for (var i = 0; i < 4; i++) {
        $scope.addSlide();
    }
}])
;*/
// DeviceDetail controller
/*
app.controller('DeviceDetailCtrl', ['$scope', '$stateParams', '$state', '$http', 'APPCONST', '$log',
    function ($scope, $stateParams, $state, $http, APPCONST, $log) {
        $scope.app.subHeader.subAsideTitle = '设备详情';
        $scope.app.subHeader.goBackHide = false;

        $scope.controlMode = '本地控制';

        $http.get(APPCONST.CTX + APPCONST.SENSOR_BY_ID + $stateParams.id).then(function (response) {
            $scope.device = response.data.data;
            // if ($scope.device.status === 'NORMAL') {
            $scope.device.sensor.dsplay_status = "正常";
            try {
                $scope.device.data.forEach(function (dataItem) {
                    if (dataItem.comp_type === 'flowmeter_sensor') {
                        dataItem.time = $scope.formatDate(new Date(dataItem.time), "yyyy年MM月dd日HH:mm:ss");
                        $scope.device.sensor.flowmeter_data = dataItem;
                    }
                });
            } catch (e) {
            }
            if ($scope.device.sensor.flowmeter_data && !$scope.device.sensor.flowmeter_data.instant) {
                $scope.device.sensor.flowmeter_data.instant = "0";
            }
            // }
            $scope.app.subHeader.contentTitle = $scope.device.sensor.name + '设备详情';
            $log.warn($scope.device.sensor);
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
            options: {chart: {type: 'line'}, tooltip: {valueSuffix: 'L/H', style: {padding: 10, fontWeight: 'bold'}}},
            series: [],
            title: {text: ''}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: ' L/H'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            useHighStocks: false, size: {height: 200}
        };
        $scope.chartHandlerDayTotal = {
            options: {chart: {type: 'column'}, tooltip: {valueSuffix: '立方米', style: {padding: 10, fontWeight: 'bold'}}},
            series: [],
            title: {text: ''}, loading: false,
            xAxis: {categories: []},
            yAxis: {title: {text: '立方米'}, plotLines: [{value: 0, width: 1, color: '#808080'}]},
            plotOptions: {bar: {dataLabels: {enabled: true}, enableMouseTracking: false}},
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

        $scope.$on("WS_MESSAGE", function (event, data) {
            // console.warn(data);
            try {
                angular.forEach(data, function (item) {
                    if (item.comp_type === 'flowmeter_sensor') {
                        item.time = $scope.formatDate(new Date(/!*item.time*!/), "yyyy年MM月dd日HH:mm:ss");
                        if (!item.instant) {
                            item.instant = "0";
                        }
                        $scope.device.sensor.flowmeter_data = item;
                    }
                });
            } catch (e) {
            }
        });

        $scope.takepick = function () {
            $scope.wsSend('{"channel_id" : "4","operation" : "take_photo","param" : 1,"sensor_id" : "59ec0ac4-2182-4960-9166-3ce62738ef99"}');
            $scope.Toast('success', '提示', '拍照成功!');
        };

        $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_TODAY.replace("{id}", $stateParams.id))
            .then(function (response) {
                try {
                    var array = response.data.data;
                    if (array) {
                        var series = {name: '瞬时流量', data: []};
                        array.forEach(function (item) {
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

        $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_TOTAL_MONTH.replace("{id}", $stateParams.id))
            .then(function (response) {
                try {
                    var data = response.data.data;
                    // console.warn(data);
                    if (data) {
                        var series = {name: '日处理量', data: data.values};
                        $scope.chartHandlerDayTotal.xAxis.categories = data.keys;
                    }
                    $scope.chartHandlerDayTotal.series.push(series);
                } catch (e) {
                    $log.debug(e);
                }
            });

        $http.get(APPCONST.CTX + APPCONST.SENSOR_DATA_IMAGE_LIST.replace("{id}", $stateParams.id))
            .then(function (response) {
                $scope.imageList = response.data.data;
                console.warn($scope.imageList);
                if ($scope.imageList) {
                    for (var i = 0; i < $scope.imageList.length; i++) {
                        $scope.imageList[i].time = $scope.formatDate(new Date($scope.imageList[i].time), "yyyy年MM月dd日HH:mm:ss")
                    }
                    $scope.imageListItemSelect(0);
                }
            });

        $scope.imageListItemSelect = function (index) {
            $scope.imageListSelectIndex = index;
            $scope.imageListSelectItem = $scope.imageList[index];
        };

    }]
)
;*/
