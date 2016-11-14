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
        $scope.selectEmployee = null;

        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 10;
        }
        $scope.total = 1;
        $scope.currentPage = 1;
        $scope.employees = [];

        $scope.resetDividerHeight = function () {
            var len = 10;
            if ($scope.employees) {
                len = len - $scope.employees.length;
            }
            $scope.dividerHeight = len <= 0 ? 0 : len * 35;
        };

        $scope.resetDividerHeight();

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.toggleSelectEmployee = function (item) {
            if ($scope.selectEmployee == item) {
                $scope.selectEmployee = null;
            } else {
                $scope.selectEmployee = item;
            }
        };

        //底部页面点击，加载每页数据
        $scope.pageChanged = function () {
            $scope.loadEmployees($scope.currentPage, $scope.size);
        };

        $scope.addEmployee = function () {
            $localStorage.isUpdate = false;
            $localStorage.selectEmployee = null;
            $state.go('app.employee.add');
        };
        $scope.editEmployee = function () {
            if (!$scope.selectEmployee) {
                return;
            }
            $localStorage.isUpdate = true;
            $localStorage.selectEmployee = $scope.selectEmployee;
            $state.go('app.employee.add');
        };
        $scope.editDuty = function () {
            if (!$scope.selectEmployee) {
                return;
            }
            $localStorage.selectEmployee = $scope.selectEmployee;
            $state.go('app.employee.duty');
        };

        $scope.loadEmployees = function (page, size) {
            $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.EMPLOYEE_LIST
                    .replace('{page}', page).replace('{size}', size))
                .then(function (response) {
                    var code = response.data.code;
                    var data = response.data.data;
                    if (code === '0') {
                        $scope.total = data.total;
                        $scope.currentPage = data.page;
                        $scope.employees = data.data;
                        if ($scope.employees) {
                            angular.forEach($scope.employees, function (item) {
                                item.birthday_display = $scope.formatDate(new Date(item.birthday), 'yyyy年MM月dd日');
                            });
                        }
                        // console.warn($scope.employees);
                    }
                    // console.warn(response);
                    $scope.resetDividerHeight();
                }, function (response) {
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                });
        };

        $scope.loadEmployees($scope.page, $scope.size);

        $scope.delEmployeeOrDelDuty = function (isDelEmployee) {
            if (!$scope.selectEmployee) {
                return;
            }
            var modalInstance = $modal.open({
                templateUrl: 'confirmModalContent.html',
                controller: 'EmployeeModalInstanceCtrl',
                resolve: {
                    content: function () {
                        return isDelEmployee ? '是否要删除员工？' : '是否要移除员工职能？';
                    }
                }
            });
            modalInstance.result.then(function () {
                if (isDelEmployee) {
                    $http.post(APPCONST.CTX + APPCONST.EMPLOYEE_REMOVE.replace("{id}", $scope.selectEmployee.id))
                        .then(function (response) {
                            toaster.pop('success', '提示', '移除员工成功。');
                            $scope.loadEmployees($scope.page, $scope.size);
                        }, function (response) {
                            toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                        });
                } else {
                    $http.post(APPCONST.CTX + APPCONST.EMPLOYEE_DUTY_REMOVE.replace("{id}", $scope.selectEmployee.id))
                        .then(function (response) {
                            toaster.pop('success', '提示', '移除员工职能成功。');
                        }, function (response) {
                            toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                        });
                }
            }, function () {
            });
        };

        $scope.modifyEmployeeResourcesDepartment = function () {
            if (!$scope.selectEmployee) {
                return;
            }
            var modalInstance = $modal.open({
                templateUrl: 'modifyEmployeeResourcesDepartmentModal.html',
                controller: 'ModifyEmployeeResDptModalInstanceCtrl',
                resolve: {
                    res: function () {
                        return !$scope.selectEmployee.resourcesLevel ? [] : $scope.selectEmployee.resourcesLevel.departments;
                    }
                }
            });
            modalInstance.result.then(function (resDpt) {
                $http.post(APPCONST.CTX + APPCONST.EMPLOYEE_RESOURCE_MODIFY.replace("{id}", $scope.selectEmployee.id),
                    {res: resDpt.join(',')}
                )
                    .then(function (response) {
                        toaster.pop('success', '提示', '修改员工资源权限成功。');
                        $scope.loadEmployees($scope.page, $scope.size);
                    }, function (response) {
                        toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                    });
            });
        };

    }]
);

app.controller('EmployeeModalInstanceCtrl', ['$scope', '$modalInstance', 'content',
    function ($scope, $modalInstance, content) {
        $scope.title = '警告';
        $scope.content = content;
        $scope.ok = function () {
            $modalInstance.close();
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }])
;

app.controller('ModifyEmployeeResDptModalInstanceCtrl', ['$scope', '$modalInstance', 'res', '$http', 'APPCONST', '$window',
    function ($scope, $modalInstance, res, $http, APPCONST, $window) {
        $scope.boxHeight = $window.innerHeight - 240;
        $scope.selections = res;
        $scope.departments = [];

        $scope.toggleSelectResDepartment = function (id) {
            var children = [];
            angular.forEach($scope.departments, function (item) {
                if (item.id == id || (item.path && item.path.indexOf(id) != -1)) {
                    children.push(item.id);
                }
            });
            // console.warn(children);
            var index = $scope.selections.indexOf(id);
            if (index != -1) {
                for (var i = 0; i < $scope.selections.length;) {
                    if (children.indexOf($scope.selections[i]) >= 0) {
                        $scope.selections.splice(i, 1);
                    } else {
                        i++;
                    }
                }
            } else {
                angular.forEach(children, function (item) {
                    $scope.selections.push(item);
                });
            }
        };
        $scope.loadDataPromise = $http.get(APPCONST.CTX + APPCONST.DEPARTMENT_LIST_ALL)
            .then(function (response) {
                var code = response.data.code;
                var data = response.data.data;
                if (code === '0') {
                    $scope.departments = data.data;
                }
            }, function (response) {
                //没有数据时候，可以添加
                toaster.pop('error', '警告', '服务器响应异常，请联系管理员。')
            });

        $scope.submit = function () {
            $modalInstance.close($scope.selections);
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }])
;