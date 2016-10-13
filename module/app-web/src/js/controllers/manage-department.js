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
app.controller('ManageDepartmentCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster', '$modal',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster, $modal) {
        $scope.buttonConfig = {add: false, edit: false, delete: false};
        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 0;
        }

        $scope.departments = [];

        $scope.resetDividerHeight = function () {
            var len = 10;
            if ($scope.departments) {
                len = 10 - $scope.departments.length;
            }
            $scope.dividerHeight = len <= 0 ? 0 : len * 35;
        };

        $scope.resetDividerHeight();

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.toggleSelectDepartment = function (item) {
            if ($scope.selectDepartment == item) {
                $scope.selectDepartment = null;
            } else {
                $scope.selectDepartment = item;
            }
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
                        $scope.departments = data.data;
                        // console.warn($scope.departments);
                    }
                    //没有数据时候，可以添加
                    // console.warn(response);
                    $scope.resetDividerHeight();
                }, function (response) {
                    //没有数据时候，可以添加
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。')
                });
        };

        $scope.loadDepartmentData($scope.page, $scope.size);

        $scope.addNode = function () {
            if ((null != $scope.departments && $scope.departments.length > 0 ) && null == $scope.selectDepartment) {
                return;
            }
            $localStorage.selectDepartment = $scope.selectDepartment;
            $localStorage.isUpdate = false;
            $state.go('app.mngdpt.edit');
        };
        $scope.editNode = function () {
            if (null == $scope.selectDepartment) {
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
            if (null == $scope.selectDepartment) {
                return;
            }
            // console.warn('click delete');
            var modalInstance = $modal.open({
                templateUrl: 'confirmModalContent.html',
                controller: 'DepartmentModalInstanceCtrl'
            });
            modalInstance.result.then(function () {
                $http.post(APPCONST.CTX + APPCONST.DEPARTMENT_DEL, {id: $scope.selectDepartment.id})
                    .then(function (response) {
                        toaster.pop('success', '提示', '节点删除成功。');
                        $scope.loadDepartmentData($scope.page, $scope.size);
                    }, function (response) {
                        toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                    });
            }, function () {
            });
        };
    }]
);


app.controller('DepartmentModalInstanceCtrl', ['$scope', '$modalInstance',
    function ($scope, $modalInstance) {
        $scope.title = '警告';
        $scope.content = '是否要删除所选节点？';
        $scope.ok = function () {
            $modalInstance.close();
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }])
;