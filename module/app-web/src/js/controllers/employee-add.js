'use strict';

/* Controllers */

app.controller('EmployeeAddTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = null == $localStorage.selectEmployee ? '添加员工' : '编辑员工信息';
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.employee';
        $scope.app.settings.asideHide = true;
    }
]);

// CencusCtrl controller
app.controller('EmployeeAddCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster) {
        $scope.format = 'yyyy-MM-dd';
        $scope.employee = $localStorage.selectEmployee;
        if (!$scope.employee) {
            $scope.employee = {password: '111111'};
        } else {
            if ($scope.employee.birthday) {
                var date = new Date($scope.employee.birthday);
                $scope.employee.birthday = $scope.formatDate(date, $scope.format);
            }
            // console.warn($scope.employee);
        }

        $scope.open = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.opened = true;
        };
        $scope.dateOptions = {formatYear: 'yy', startingDay: 1, class: 'datepicker'};

        $scope.Toast = function (type, title, text) {
            toaster.pop(type, title, text);
        };

        $scope.submit = function () {
            // console.warn('submit');
            if (!$scope.employee.number) {
                $scope.Toast('warning', '提示', '请输入员工工号');
                return;
            }
            if (!$scope.employee.username) {
                $scope.Toast('warning', '提示', '请输入登录帐号');
                return;
            }
            if (!$scope.employee.password) {
                $scope.Toast('warning', '提示', '请输入员工密码');
                return;
            }
            if (!$scope.employee.name) {
                $scope.Toast('warning', '提示', '请输入员工姓名');
                return;
            }
            if (!$scope.employee.sex) {
                $scope.Toast('warning', '提示', '请选择员工性别');
                return;
            }
            if (!$scope.employee.status) {
                $scope.Toast('warning', '提示', '请选择员工帐号状态');
                return;
            }

            var url = "";
            var data = null;
            //添加
            if (null == $localStorage.selectUser) {
                url = APPCONST.CTX + APPCONST.EMPLOYEE_ADD;
                data = $scope.employee;
            } else {
                //修改
            }

            $http.post(url, data).then(function (response) {
                // console.warn(response);
                if (response.data.code === '0') {
                    $scope.Toast('success', '消息', null == $localStorage.selectEmployee ?
                        '添加用户成功。' : '修改用户成功');
                    $state.go('app.employee');
                }
            }, function (response) {
                // console.warn(response);
                $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
            });
        };
    }]
);