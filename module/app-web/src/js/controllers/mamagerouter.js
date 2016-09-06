'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageRouterCtrl', ['$scope', '$http', '$localStorage', '$modal', 'APPCONST', '$state',
    function ($scope, $http, $localStorage, $modal, APPCONST, $state) {
        $scope.app.subHeader.subAsideTitle = '人员列表';
        $scope.app.subHeader.contentTitle = '人员数据';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;

        $scope.TYPEDEPARMENT = 'department';
        $scope.selectItemId = null;
        $scope.selectItem = null;
        $scope.buttonConfig = {addNode: true, addEmployee: false, edit: false, delete: false};

        $scope.employeList = [];
        $scope.employeHidePathList = {};
        $scope.employeRootPathList = {};

        $scope.resetVisableEmployee = function () {
            // console.warn($scope.employeHidePathList);
            $scope.employeList.forEach(function (item) {
                item.visible = true;
                if (item.path) {
                    for (var k in $scope.employeHidePathList) {
                        var v = $scope.employeHidePathList[k];
                        if (item.path && item.path.indexOf(k) != -1) {
                            item.visible = false;
                        }
                    }
                }
            });
        };

        $scope.prepareVisableEmployee = function () {
            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_LIST)
                .then(function (response) {
                    var data = response.data.data;
                    $scope.buttonConfig.addNode = data && data.length <= 0;
                    // console.warn(data);

                    $scope.employeList.splice(0);
                    $scope.employeRootPathList = {};
                    $scope.selectItemId = null;
                    $scope.selectItem = null;
                    if (data) {
                        var ppath, path, pid;
                        angular.forEach(data, function (item) {
                            path = item.path;
                            pid = item.parent_id;
                            if (path && typeof $scope.employeRootPathList[pid] === 'undefined') {
                                $scope.employeRootPathList[pid] = 1;
                            }
                            item.visible = true;
                            $scope.employeList.push(item);
                        });
                    }
                    $scope.buttonConfig.addNode = $scope.employeList.length <= 0;
                    $scope.resetVisableEmployee();
                }, function (response) {
                });
        };


        $scope.toggleEmployee = function (id) {
            if (!$scope.employeHidePathList[id]) {
                $scope.employeHidePathList[id] = 1;
            } else {
                delete $scope.employeHidePathList[id];
            }
            $scope.resetVisableEmployee();
        };
        $scope.toggleRowSelect = function (id) {
            if ($scope.selectItemId == id) {
                $scope.selectItemId = null;
                $scope.selectItem = null;
                $scope.buttonConfig.addNode = $scope.employeList.length <= 0;
                $scope.buttonConfig.addEmployee = false;
                $scope.buttonConfig.delete = false;
                $scope.buttonConfig.edit = false;
            } else {
                $scope.buttonConfig.delete = true;
                $scope.buttonConfig.edit = true;
                $scope.selectItemId = id;
                if ($scope.employeList) {
                    for (var i = 0; i < $scope.employeList.length; i++) {
                        if ($scope.employeList[i].id === id) {
                            $scope.selectItem = $scope.employeList[i];
                            break;
                        }
                    }
                    if ($scope.selectItem) {
                        if ($scope.selectItem.type === $scope.TYPEDEPARMENT) {
                            $scope.buttonConfig.addNode = true;
                            $scope.buttonConfig.addEmployee = true;
                        } else {
                            $scope.buttonConfig.addNode = false;
                            $scope.buttonConfig.addEmployee = false;
                        }
                    }
                }
            }
            // console.warn($scope.selectItem);
        };

        $scope.addNode = function () {
            if (!$scope.buttonConfig.addNode) {
                return;
            }
            if ($scope.selectItem == null && $scope.employeList && $scope.employeList.length > 0) {
                $scope.Toast("warning", "警告", "请选择父节点");
                return;
            }
            if ($scope.selectItem && $scope.selectItem.type !== $scope.TYPEDEPARMENT) {
                $scope.Toast("warning", "警告", "请选择区域节点");
                return;
            }
            $scope.updateNode(true);
        };
        $scope.addEmployee = function () {
            if (!$scope.buttonConfig.addEmployee) {
                return;
            }
            if ($scope.selectItem == null && $scope.employeList && $scope.employeList.length > 0) {
                $scope.Toast("warning", "警告", "请选择父节点");
                return;
            }
            if ($scope.selectItem.type !== $scope.TYPEDEPARMENT) {
                $scope.Toast("warning", "警告", "请选择区域节点");
                return;
            }
            $scope.updateEmployee(true);
        };
        $scope.edit = function () {
            if (!$scope.buttonConfig.edit) {
                return;
            }
            if ($scope.selectItem == null && $scope.employeList && $scope.employeList.length > 0) {
                $scope.Toast("warning", "警告", "请选择需要编辑的节点");
                return;
            }
            if ($scope.selectItem.type === $scope.TYPEDEPARMENT) {
                $scope.updateNode(false);
            } else {

            }
        };
        $scope.delete = function () {
            if (!$scope.buttonConfig.delete) {
                return;
            }
            if ($scope.selectItem == null && $scope.employeList && $scope.employeList.length > 0) {
                $scope.Toast("warning", "警告", "请选择需要被删除的节点");
                return;
            }
            var content = '';
            if ($scope.selectItem.type === $scope.TYPEDEPARMENT) {
                content = '确定要删除 ' + $scope.selectItem.name + ' 节点么？';
            } else {
                content = '确定要删除人员 ' + $scope.selectItem.name + ' 么？';
            }
            var modalInstance = $modal.open({
                templateUrl: 'confirmModalContent.html',
                controller: 'ComfirmModalCtrl',
                resolve: {
                    title: function () {
                        return '提示';
                    },
                    content: function () {
                        return content;
                    }
                }
            });
            modalInstance.result.then(function () {
                if ($scope.selectItem.type === $scope.TYPEDEPARMENT) {
                    $http.delete(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_DELETE.replace(
                            "{id}", $scope.selectItemId))
                        .then(function (response) {
                            $scope.Toast('success', '提示', '删除成功。');
                            $scope.prepareVisableEmployee();
                        }, function (response) {
                        });
                } else {
                    $http.delete(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_DELETE_EMP.replace(
                            "{id}", $scope.selectItem.employee_id))
                        .then(function (response) {
                            $scope.Toast('success', '提示', '删除成功。');
                            $scope.prepareVisableEmployee();
                        }, function (response) {
                        });
                }

            }, function () {
            });
        };

        $scope.updateNode = function (create) {
            var modalInstance = $modal.open({
                templateUrl: 'addNodeModalContent.html',
                controller: 'AddEmployeeNodeModalInstanceCtrl',
                backdrop: 'static',
                resolve: {
                    create: function () {
                        return create;
                    },
                    node: function () {
                        return $scope.selectItem;
                    }
                }
            });
            modalInstance.result.then(function (node) {
                $scope.prepareVisableEmployee();
            }, function (type) {
                if (type === 'error') {
                    $scope.Toast('error', '错误', '添加失败，服务器响应异常。');
                }
            });
        };

        $scope.updateEmployee = function (create) {
            /*var modalInstance = $modal.open({
             templateUrl: 'addEmployeeModalContent.html',
             controller: 'AddEmployeeModalInstanceCtrl',
             backdrop: 'static',
             resolve: {
             create: function () {
             return create;
             },
             node: function () {
             return $scope.selectItem;
             }
             }
             });
             modalInstance.result.then(function (node) {
             $scope.prepareVisableEmployee();
             }, function (type) {
             if (type === 'error') {

             }
             });*/
            $localStorage[APPCONST.APP_LOCAL_STORAGE_MANAGE_EMPLOYEE] = {create: create, node: $scope.selectItem};
            $state.go('app.mr.emp');
        };

        $scope.prepareVisableEmployee();
    }]
);

app.controller('AddEmployeeNodeModalInstanceCtrl',
    ['$scope', '$modalInstance', '$http', 'node', 'create', 'APPCONST',
        function ($scope, $modalInstance, $http, node, create, APPCONST) {
            $scope.title = create ? "增加区域节点" : "修改区域节点";
            var nid = node == null ? null : node.id;
            var path = node == null ? null : node.path;
            var pl = node == null ? 0 : node.level;
            $scope.node = {pl: pl, pid: nid, ppath: path};
            $scope.submit = function () {
                $scope.submitPromise = $http.post(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_ADD, $scope.node)
                    .then(function (response) {
                        $scope.ok();
                    }, function (response) {
                        $scope.error();
                    });
            };
            $scope.ok = function () {
                $modalInstance.close($scope.node);
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            $scope.error = function () {
                $modalInstance.dismiss('error');
            };
        }]);

app.controller('AddEmployeeModalInstanceCtrl',
    ['$scope', '$modalInstance', '$http', 'node', 'create', 'APPCONST',
        function ($scope, $modalInstance, $http, node, create, APPCONST) {
            $scope.title = create ? "增加区域节点" : "修改区域节点";
            var nid = node == null ? null : node.id;
            var path = node == null ? null : node.path;
            var pl = node == null ? 0 : node.level;
            $scope.node = {pl: pl, pid: nid, ppath: path};
            $scope.submit = function () {
                $scope.submitPromise = $http.post(APPCONST.CTX + APPCONST.EMP_DEPARTMENT_ADD, $scope.node)
                    .then(function (response) {
                        $scope.ok();
                    }, function (response) {
                        $scope.ok();
                    });
            };
            $scope.ok = function () {
                $modalInstance.close($scope.node);
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);