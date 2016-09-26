'use strict';

/* Controllers */

app.controller('EmployeeTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = '员工管理';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;
    }
]);

// CencusCtrl controller
app.controller('EmployeeCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state', 'toaster',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state, toaster) {
        $scope.selectUser = null;

        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 10;
        }
        $scope.toggleUserSelect = function (user) {
            if ($scope.selectUser === user) {
                $scope.selectUser = null;
            } else {
                $scope.selectUser = user;
            }
            console.warn('select user');
        };
        $scope.addEmployee = function () {
            $localStorage.selectUser = null;
            $state.go('app.employee.add');
        };
        $scope.editEmployee = function () {
            $localStorage.selectUser = $scope.selectUser;
            $state.go('app.employee.add');
        };
        $scope.deleteEmployee = function () {

        };

        $scope.loadUser = function (page, size) {
            $http.get(APPCONST.CTX + APPCONST.EMPLOYEE_LIST
                    .replace("{page}", $scope.page)
                    .replace("{size}", $scope.size))
                .then(function (response) {
                    console.warn(response);
                    var code = response.data.code;
                    var respObject = response.data.data;
                    if (code === '0') {
                        $scope.users = respObject.data;
                    }
                }, function (response) {
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                });
        };

        $scope.loadUser($scope.page, $scope.size);

        /*$http.get(APPCONST.CTX + APPCONST.SYSTEM_INFORMATION)
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
         // console.warn($scope.sysinfo);
         }, function (response) {
         });*/
    }]
);