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
        $scope.isUpdate = $localStorage.isUpdate;
        $scope.format = 'yyyy-MM-dd';
        $scope.employee = $localStorage.selectEmployee;
        if (!$scope.isUpdate) {
            $scope.employee = {password: '111111', status: 1, type: 5, birthday: new Date(), sex: 1};
        } else {
            if ($scope.employee.birthday) {
                $scope.employee.birthday = new Date($scope.employee.birthday);
            }
        }

        $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_LIST_ALL).then(function (response) {
            if (response.data.data) {
                $scope.departments = response.data.data.data;
            }
            // console.warn($scope.departments);
        }, function (response) {
            $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
        });

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
            var data = data = angular.extend({}, $scope.employee);
            //添加
            if ($scope.isUpdate) {
                url = APPCONST.CTX + APPCONST.EMPLOYEE_UPDATE;
            } else {
                //修改
                url = APPCONST.CTX + APPCONST.EMPLOYEE_ADD;
            }
            if (data.type == 1) {
                data.dt = JSON.stringify(data.duty);
            }
            delete data.duty;
            data.birthday = $scope.formatDate(data.birthday, "yyyy-MM-dd");
            // console.warn(data);

            $http.post(url, data).then(function (response) {
                // console.warn(response);
                if (response.data.code === '0') {
                    $scope.Toast('success', '消息', $scope.isUpdate ? '修改用户成功' : '添加用户成功。');
                    $state.go('app.employee');
                } else if (response.data.code === '201') {
                    $scope.Toast('error', response.data.message);
                } else {
                    $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。' + response.data.message);
                }
            }, function (response) {
                // console.warn(response);
                $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
            });
        };
    }]
);