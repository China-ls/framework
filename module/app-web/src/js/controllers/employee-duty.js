'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('EmployeeDutyTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state) {
        $scope.app.subHeader.subAsideTitle = '员工列表';
        $scope.app.subHeader.contentTitle = '员工数据';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;
    }]);

app.controller('EmployeeDutyCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state) {
        $scope.buttonConfig = {add: false, edit: false, delete: false};
        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 10;
        }

        $scope.total = 64;
        $scope.currentPage = 4;
        $scope.employees = [];

        $scope.resetDividerHeight = function () {
            var len = 10 - $scope.employees.length;
            $scope.dividerHeight = len <= 0 ? 0 : len * 35;
        };

        $scope.resetDividerHeight();

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.toggleSelectEmployee = function (item) {
            var isSelected = false;
            if ($scope.selectEmployee == item) {
                $scope.selectEmployee = null;
            } else {
                $scope.selectEmployee = item;
                isSelected = true;
            }
            $scope.buttonConfig.add = isSelected || $scope.employees === null || $scope.employees.length <= 0;
            $scope.buttonConfig.edit = $scope.buttonConfig.delete = isSelected;
        };

        //底部页面点击，加载每页数据
        $scope.pageChanged = function () {
            $scope.loadDepartmentData($scope.currentPage, $scope.size);
        };

        $scope.loadDepartmentData = function (page, size) {
            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.EMPLOYEE_LIST
                    .replace('{page}', page).replace('{size}', size))
                .then(function (response) {
                    var code = response.data.code;
                    var data = response.data.data;
                    if (code === '0') {
                        $scope.total = data.total;
                        $scope.currentPage = data.page;
                        $scope.employees = data.data;
                        console.warn($scope.employees);
                    }
                    //没有数据时候，可以添加
                    $scope.buttonConfig.add = null == $scope.employees || $scope.employees.length <= 0;
                    // console.warn(response);
                    $scope.resetDividerHeight();
                }, function (response) {
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。')
                });
        };

        $scope.loadDepartmentData($scope.page, $scope.size);

        $scope.addNode = function () {
            $localStorage.selectDepartment = $scope.selectDepartment;
            $localStorage.isUpdate = false;
            $state.go('app.mngdpt.edit');
        };
        $scope.editNode = function () {
            $localStorage.selectDepartment = $scope.selectDepartment;
            var pid = $scope.selectDepartment.parentId;
            $localStorage.selectDepartmentParentType = null;
            angular.forEach($scope.employees, function (item) {
                if (item.id === pid) {
                    $localStorage.selectDepartmentParentType = item.type;
                }
            });
            $localStorage.isUpdate = true;
            $state.go('app.mngdpt.edit');
        };
        $scope.deleteNode = function () {
            $localStorage.selectDepartment = $scope.selectDepartment;

        };
    }]
);