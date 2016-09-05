'use strict';

/* Controllers */

// CencusCtrl controller
app.controller('CencusCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = '站点数据统计分析';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;

        $scope.chartHandlerDeviceType = {
            options: {
                chart: {type: 'pie'},
                tooltip: {pointFormat: '<b>{point.y} 个</b>', style: {padding: 10, fontWeight: 'bold'}}
            },
            series: [{
                type: 'pie',
                data: [
                    ['A类站点', 26],
                    ['B类站点', 32],
                    ['C类站点', 30]
                ], dataLabels: {enabled: true, format: '{point.percentage:.1f}%'},
            }],
            title: {text: ''}, loading: false,
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer'
                }
            },
            useHighStocks: false, size: {height: 300}
        };


        $scope.chartHandlerDeviceStatus = {
            options: {
                chart: {type: 'pie'},
                tooltip: {pointFormat: '<b>{point.y} 个</b>', style: {padding: 10, fontWeight: 'bold'}}
            },
            series: [{
                type: 'pie',
                data: [
                    ['正常站点', 2],
                    ['离线站点', 2]
                ], dataLabels: {enabled: true, format: '{point.percentage:.1f}%'}
            }],
            title: {text: ''}, loading: false,
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer'
                }
            },
            useHighStocks: false, size: {height: 300}
        };

        $scope.device = [
            {name:'张家村站',type:'A类',dayHandler:'10吨', time:'2015.01.01',contact:'张大磊', phone:'13764098745',twon:'西塘镇', city:'张家村'},
            {name:'赵家庄站',type:'A类',dayHandler:'15吨', time:'2015.05.01',contact:'李国发', phone:'12809847234',twon:'泖港镇', city:'赵家庄'},
            {name:'张家村站',type:'A类',dayHandler:'11吨', time:'2015.05.01',contact:'张大磊', phone:'13764098745',twon:'西塘镇', city:'张家村'},
        ];
    }]
);