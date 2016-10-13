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
            var len = 10 - $scope.employees.length;
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
            $localStorage.selectEmployee = null;
            $state.go('app.employee.add');
        };
        $scope.editEmployee = function () {
            if (!$scope.selectEmployee) {
                return;
            }
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
                        console.warn($scope.employees);
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