'use strict';

/* Controllers */

app.controller('RoleAddTitleCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST',
    function ($scope, $http, $localStorage, $modal, APPCONST) {
        $scope.app.subHeader.contentTitle = null == $localStorage.selectRole ? '添加角色' : '编辑角色信息';
        $scope.app.subHeader.goBackHide = false;
        $scope.app.subHeader.goBackSref = 'app.role';
        $scope.app.settings.asideHide = true;
    }
]);

// CencusCtrl controller
app.controller('RoleAddCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster) {
        $scope.format = 'yyyy-MM-dd';
        $scope.isUpdate = $localStorage.isUpdate || false;
        $scope.roles = $localStorage.roles;
        $scope.role = {};
        $scope.entity = {};

        $scope.onParentChange = function (parent) {
            // console.warn(parent);
            if (null == parent) {
                $scope.role = {};
            } else {
                $scope.p_id = parent.id;
                $scope.role.p_id = parent.id;
                $scope.role.p_path = parent.path;
                $scope.role.p_name = parent.name;
                $scope.role.p_name_path = parent.namePath;
                $scope.role.p_level = parent.level;
            }
            // console.warn($scope.role);
        };

        $scope.$watch('p_id', function (newValue, oldValue, scope) {
            angular.forEach($scope.roles, function (item) {
                if (item.id == newValue) {
                    $scope.onParentChange(item);
                }
            });
        });

        if ($scope.isUpdate) {
            $scope.role = $localStorage.selectRole;
            var filterRole = [];
            var id = $scope.role.id;
            angular.forEach($scope.roles, function (item) {
                if (item.id != id && item.path.indexOf(id) === -1) {
                    filterRole.push(item);
                }
            });
            $scope.roles = filterRole;
            angular.forEach($scope.roles, function (item) {
                if (item.id == $scope.role.parentId) {
                    $scope.onParentChange(item);
                }
            });
        } else {
            $scope.role.status = 1;
            $scope.onParentChange($localStorage.selectRole);
        }

        $scope.selectPermission = function (id) {
            var children = [];
            angular.forEach($scope.permissions, function (item) {
                if (item.id == id || item.path.indexOf(id) != -1) {
                    children.push(item.id);
                }
            });
            // console.warn(children);

            var index = $scope.keys.indexOf(id);
            if (index != -1) {
                for (var i = 0; i < $scope.keys.length;) {
                    if (children.indexOf($scope.keys[i]) >= 0) {
                        $scope.keys.splice(i, 1);
                    } else {
                        i++;
                    }
                }
            } else {
                angular.forEach(children, function (item) {
                    $scope.keys.push(item);
                });
            }
        };

        $http.get(APPCONST.CTX + APPCONST.ROLE_PERMISSION_LIST + ($scope.role.id ? '?id=' + $scope.role.id : '') )
            .then(function (response) {
                // console.warn(response);
                var data = response.data.data;
                if (!data) {
                    return;
                }
                $scope.keys = data.keys || [];
                $scope.permissions = data.permissions;
                $scope.permissionMap = {};
                if ($scope.permissions) {
                    angular.forEach($scope.permissions, function (item) {
                        item.level = item.path.split(';').length - 1;
                        $scope.permissionMap[item.id] = item;
                        // console.warn(item);
                    });
                }
            }, function (response) {
                $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
            });

        $scope.Toast = function (type, title, text) {
            toaster.pop(type, title, text);
        };

        $scope.submit = function () {
            // console.warn('submit');
            if (!$scope.role.name) {
                $scope.Toast('warning', '提示', '请输入角色名称');
                return;
            }
            if (!$scope.role.description) {
                $scope.Toast('warning', '提示', '请输入角色描述');
                return;
            }
            if (!$scope.role.status) {
                $scope.Toast('warning', '提示', '请输入角色状态');
                return;
            }

            var url = "";
            var data = data = $scope.role;
            data.pks = $scope.keys.join(',');
            if ($scope.isUpdate) {
                //修改
                url = APPCONST.CTX + APPCONST.ROLE_UPDATE;
                data.permissions = null;
            } else {
                //添加
                url = APPCONST.CTX + APPCONST.ROLE_ADD;
            }

            console.warn(data);

            $http.post(url, data).then(function (response) {
                // console.warn(response);
                if (response.data.code === '0') {
                    $scope.Toast('success', '消息', null == $localStorage.selectRole ? '添加角色成功。' : '修改角色成功');
                    $state.go('app.role');
                }
            }, function (response) {
                // console.warn(response);
                $scope.Toast('error', '警告', '服务器响应异常，请联系管理员。');
            });
        };
    }]
);