'use strict';

/* Controllers */

// ManageRouterCtrl controller
app.controller('ManageDeviceTitleCtrl', ['$scope', '$http', '$localStorage',
    function ($scope, $http, $localStorage) {
        $scope.app.subHeader.subAsideTitle = '设备列表';
        $scope.app.subHeader.contentTitle = '设备数据';
        $scope.app.subHeader.goBackHide = true;
        $scope.app.settings.asideHide = true;
    }]
);

app.controller('ManageDeviceCtrl', ['$scope', '$http', '$localStorage', '$state', 'APPCONST', 'toaster', '$modal',
    function ($scope, $http, $localStorage, $state, APPCONST, toaster, $modal) {
        if (!$scope.page) {
            $scope.page = 1;
        }
        if (!$scope.size) {
            $scope.size = 10;
        }

        $scope.toggleSelectDevice = function (item) {
            if (item == $scope.selectDevice) {
                $scope.selectDevice = null;
            } else {
                $scope.selectDevice = item;
            }
        };

        $scope.addDevice = function () {
            $localStorage.isUpdate = false;
            $localStorage.selectDevice = null;
            $state.go('app.mngdevice.modify');
        };
        $scope.editDevice = function () {
            if (null == $scope.selectDevice) {
                return;
            }
            $localStorage.isUpdate = true;
            $localStorage.selectDevice = $scope.selectDevice;
            $state.go('app.mngdevice.modify');
        };
        $scope.delDevice = function () {
            if (null == $scope.selectDevice) {
                return;
            }
            var modalInstance = $modal.open({
                templateUrl: 'confirmModalContent.html',
                controller: 'DeviceDeleteModalInstanceCtrl'
            });
            modalInstance.result.then(function () {
                    $http.post(APPCONST.CTX + APPCONST.SENSOR_REMOVE, {id: $scope.selectDevice.sensor_id}).then(function (response) {
                        toaster.pop('success', '提示', '移除设备成功。');
                        $scope.loadDevices($scope.page, $scope.size);
                    }, function (response) {
                        toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                    });
                }, function () {
                    toaster.pop('success', '提示', '取消移除设备。');
                }
            );
        };

        $scope.loadDevices = function (page, size) {
            $http.get(APPCONST.CTX + APPCONST.SENSOR_LIST_PAGE.replace('{page}', page).replace('{size}', size))
                .then(function (response) {
                    var code = response.data.code;
                    var pagedata = response.data.data;
                    if (code === '0') {
                        $scope.devices = pagedata.data;
                    }
                    console.warn(response);
                }, function (response) {
                    toaster.pop('error', '警告', '服务器响应异常，请联系管理员。');
                });
        };
        $scope.loadDevices($scope.page, $scope.size);
    }]
)
;


app.controller('DeviceDeleteModalInstanceCtrl', ['$scope', '$modalInstance',
    function ($scope, $modalInstance) {
        $scope.title = '警告';
        $scope.content = '是否要移除设备';
        $scope.ok = function () {
            $modalInstance.close();
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }])
;