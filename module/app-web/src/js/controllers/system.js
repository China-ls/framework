'use strict';

/* Controllers */

// CencusCtrl controller
app.controller('SystemInformationCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = '系统信息';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;

        var KB = 1024 * 1024;
        var M = 1024 * 1024 * 1024;
        var G = 1024 * 1024 * 1024 * 1024;
        var T = 1024 * 1024 * 1024 * 1024 * 1024;

        var second = 1000;
        var minute = 60 * second;
        var hour = 60 * minute;
        var day = 24 * hour;
        var month = 30 * day;

        var formatStorage = function (len) {
            if (len < KB) {
                return len + ' bytes';
            } else if (len < M) {
                return parseInt(len / KB * 100) / 100.0 + ' MB';
            } else if (len < G) {
                return parseInt(len / M * 100) / 100.0 + ' GB';
            } else {
                return parseInt(len / G * 100) / 100.0 + ' TB';
            }
        };

        //10923748
        var formatDateDistance = function (date) {
            if (date < second) {
                return date + ' 毫秒';
            } else if (date < minute) {
                return parseInt(date / second * 100) / 100.0 + ' 秒';
            } else if (date < hour) {
                return parseInt(date / minute * 100) / 100.0 + ' 分钟';
            } else if (date < day) {
                return parseInt(date / hour * 100) / 100.0 + ' 小时';
            } else if (date < month) {
                return parseInt(date / day * 100) / 100.0 + ' 天';
            } else {
                return parseInt(date / month * 100) / 100.0 + ' 月';
            }
        };

        $http.get(NETCONST.CTX + NETCONST.SYSTEM_INFORMATION)
            .then(function (response) {
                $scope.sysinfo = response.data;
                if ($scope.sysinfo) {
                    $scope.sysinfo.jvm_metrics.os_swap_free = formatStorage($scope.sysinfo.jvm_metrics.os_swap_free);
                    $scope.sysinfo.jvm_metrics.heap_memory.alloc = formatStorage($scope.sysinfo.jvm_metrics.heap_memory.alloc);
                    $scope.sysinfo.jvm_metrics.heap_memory.max = formatStorage($scope.sysinfo.jvm_metrics.heap_memory.max);
                    $scope.sysinfo.jvm_metrics.heap_memory.used = formatStorage($scope.sysinfo.jvm_metrics.heap_memory.used);
                    $scope.sysinfo.jvm_metrics.non_heap_memory.alloc = formatStorage($scope.sysinfo.jvm_metrics.non_heap_memory.alloc);
                    $scope.sysinfo.jvm_metrics.non_heap_memory.max = formatStorage($scope.sysinfo.jvm_metrics.non_heap_memory.max);
                    $scope.sysinfo.jvm_metrics.non_heap_memory.used = formatStorage($scope.sysinfo.jvm_metrics.non_heap_memory.used);
                    $scope.sysinfo.jvm_metrics.os_memory_free = formatStorage($scope.sysinfo.jvm_metrics.os_memory_free);
                    $scope.sysinfo.jvm_metrics.os_memory_total = formatStorage($scope.sysinfo.jvm_metrics.os_memory_total);
                    $scope.sysinfo.jvm_metrics.start_time = $scope.formatDate(new Date($scope.sysinfo.jvm_metrics.start_time), 'yyyy年MM月dd日HH:mm:ss');
                    $scope.sysinfo.jvm_metrics.uptime = formatDateDistance($scope.sysinfo.jvm_metrics.uptime);
                }
                console.warn($scope.sysinfo);
            }, function (response) {
            });
    }]
);