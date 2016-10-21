'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('DepartmentEditTitleCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST',
    function ($scope, $http, $localStorage, $state, APPCONST) {
        $scope.stateParams = $localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE];
        $scope.app.subHeader.contentTitle =
            (null == $localStorage.isUpdate || $localStorage.isUpdate)
                ? '添加机构' : '修改机构';
        $scope.app.settings.asideHide = true;
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.mngdpt';
    }]
);
app.controller('DepartmentEditCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster) {
        $scope.legalTypeConfig = [true, false, false, false];
        $scope.isUpdate = $localStorage.isUpdate || false;
        $scope.p_type = null;
        if ($scope.isUpdate) {
            $scope.node = $localStorage.selectDepartment;
            $scope.p_type = $localStorage.selectDepartmentParentType;
        } else {
            if (null != $localStorage.selectDepartment) {
                $scope.node = {
                    p_id: $localStorage.selectDepartment.id,
                    p_name: $localStorage.selectDepartment.name,
                    p_name_path: $localStorage.selectDepartment.name_path,
                    p_level: $localStorage.selectDepartment.level,
                    p_path: $localStorage.selectDepartment.path
                };
                $scope.p_type = $localStorage.selectDepartment.type;
            } else {
                $scope.node = {};
            }
        }

        $scope.warnToast = function (text) {
            toaster.pop('warning', '提示', text);
        };

        $scope.submit = function () {
            if (!$scope.node.type) {
                $scope.warnToast('请选择机构类型');
                return;
            }
            if (!$scope.node.name) {
                $scope.warnToast('请输入机构名字');
                return;
            }

            $http.put(APPCONST.CTX + APPCONST.DEPARTMENT_ADD, $scope.node).then(function (response) {
                // console.warn(response);
                if (response.data.code === '0') {
                    toaster.pop('success', '提示', '机构添加成功。');
                    $state.go('app.mngdpt');
                }
            }, function (response) {
                toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
            });

        };

    }]
);