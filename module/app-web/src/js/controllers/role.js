'use strict';

/* Controllers */

app.controller('RoleTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = '权限管理';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;
    }
]);

// CencusCtrl controller
app.controller('RoleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state', 'toaster',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state, toaster) {
        $scope.selectRole = null;
        $scope.roles = [];

        $scope.toggleSelectRole = function (item) {
            if ($scope.selectRole == item) {
                $scope.selectRole = null;
            } else {
                $scope.selectRole = item;
            }
        };

        $scope.addRole = function () {
            if (!$scope.selectRole) {
                return;
            }
            $localStorage.roles = $scope.roles;
            $localStorage.selectRole = $scope.selectRole;
            $localStorage.isUpdate = false;
            $state.go('app.role.modify');
        };
        $scope.editRole = function () {
            if (!$scope.selectRole) {
                return;
            }
            $localStorage.selectRole = $scope.selectRole;
            $localStorage.roles = $scope.roles;
            $localStorage.isUpdate = true;
            $state.go('app.role.modify');
        };
        $scope.delRole = function () {
            if (!$scope.selectRole) {
                return;
            }
            var modalInstance = $modal.open({
                templateUrl: 'confirmModalContent.html',
                controller: 'RoleModalInstanceCtrl'
            });
            modalInstance.result.then(function () {
                $http.post(APPCONST.CTX + APPCONST.ROLE_DEL, {id: $scope.selectRole.id})
                    .then(function (response) {
                        toaster.pop('success', '提示', '移除角色成功。');
                        $scope.loadRoles();
                    }, function (response) {
                        toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                    });
            }, function () {
            });
        };

        $scope.loadRoles = function () {
            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.ROLE_LIST)
                .then(function (response) {
                    var code = response.data.code;
                    var data = response.data.data;
                    if (code === '0' && null != data) {
                        $scope.roles = data;
                        if ($scope.roles) {
                            angular.forEach($scope.roles, function (item) {
                                if (item.permissions) {
                                    var text = '';
                                    for (var i = 0; i < item.permissions.length; i ++) {
                                        text += item.permissions[i].name;
                                        if (i != item.permissions.length - 1) {
                                            text += ' | ';
                                        }
                                    }
                                    item.popover = text;
                                }
                            });
                        }
                        // console.warn($scope.employees);
                    }
                    // console.warn(response);
                }, function (response) {
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                });
        };

        $scope.loadRoles();

    }]
);

app.controller('RoleModalInstanceCtrl', ['$scope', '$modalInstance',
    function ($scope, $modalInstance) {
        $scope.title = '警告';
        $scope.content = '是否要移除角色';
        $scope.ok = function () {
            $modalInstance.close();
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }])
;