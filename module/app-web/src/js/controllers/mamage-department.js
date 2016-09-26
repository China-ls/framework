'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageDepartmentTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE];
        $scope.app.subHeader.contentTitle = '机构数据';
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = true;
    }]
);
app.controller('ManageDepartmentCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster) {
        $scope.buttonConfig = {add: false, edit: false, delete: false};
        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 10;
        }

        $scope.total = 0;
        $scope.currentPage = 1;
        $scope.departments = [];

        $scope.resetDividerHeight = function () {
            var len = 10 - $scope.departments.length;
            $scope.dividerHeight = len <= 0 ? 0 : len * 35;
        };

        $scope.resetDividerHeight();

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.toggleSelectDepartment = function (item) {
            var isSelected = false;
            if ($scope.selectDepartment == item) {
                $scope.selectDepartment = null;
            } else {
                $scope.selectDepartment = item;
                isSelected = true;
            }
            $scope.buttonConfig.add = isSelected || $scope.departments === null || $scope.departments.length <= 0;
            $scope.buttonConfig.edit = $scope.buttonConfig.delete = isSelected;
        };

        //底部页面点击，加载每页数据
        $scope.pageChanged = function () {
            $scope.loadDepartmentData($scope.currentPage, $scope.size);
        };

        $scope.loadDepartmentData = function (page, size) {
            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_LIST
                    .replace('{page}', page).replace('{size}', size))
                .then(function (response) {
                    var code = response.data.code;
                    var data = response.data.data;
                    if (code === '0') {
                        $scope.total = data.total;
                        $scope.currentPage = data.page;
                        $scope.departments = data.data;
                        console.warn($scope.departments);
                    }
                    //没有数据时候，可以添加
                    $scope.buttonConfig.add = null == $scope.departments || $scope.departments.length <= 0;
                    // console.warn(response);
                    $scope.resetDividerHeight();
                }, function (response) {
                    //没有数据时候，可以添加
                    $scope.buttonConfig.add = $scope.buttonConfig.edit = $scope.buttonConfig.delete = false;
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。')
                });
        };

        $scope.loadDepartmentData($scope.page, $scope.size);

        $scope.addNode = function () {
            if (!$scope.buttonConfig.add) {
                // toaster.pop('warning', '提示', '请先选择一个机构。');
                return;
            }
            $localStorage.selectDepartment = $scope.selectDepartment;
            $localStorage.isUpdate = false;
            $state.go('app.mngdpt.edit');
        };
        $scope.editNode = function () {
            if (!$scope.buttonConfig.edit) {
                // toaster.pop('warning', '提示', '请选择需要修改的机构。');
                return;
            }
            $localStorage.selectDepartment = $scope.selectDepartment;
            var pid = $scope.selectDepartment.parentId;
            $localStorage.selectDepartmentParentType = null;
            angular.forEach($scope.departments, function (item) {
                if (item.id === pid) {
                    $localStorage.selectDepartmentParentType = item.type;
                }
            });
            $localStorage.isUpdate = true;
            $state.go('app.mngdpt.edit');
        };
        $scope.deleteNode = function () {
            if (!$scope.buttonConfig.delete) {
                // toaster.pop('warning', '提示', '请选择需要删除的机构。');
                return;
            }
            // console.warn('click delete');
            $localStorage.selectDepartment = $scope.selectDepartment;
        };
    }]
);